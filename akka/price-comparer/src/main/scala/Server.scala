import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import akka.stream.scaladsl.{Sink, Source}

import scala.collection.mutable
import scala.concurrent.duration._
import scala.util.{Failure, Random, Success}
import akka.actor.typed.scaladsl.AskPattern._
import akka.util.Timeout

sealed trait ServerCommand
case class SearchProduct(name: String, client: ActorRef[ProductsResponse]) extends ServerCommand
case class Occurrences(query: String, occurrences: Int, requestId: Int) extends ServerCommand
case class SearchProductReviews(query: String, client: ActorRef[ReviewResponse]) extends ServerCommand
case class ProductsReviews(query: String, reviews: String) extends ServerCommand

class OccurrencesRequest(val prices: Seq[Int], val client: ActorRef[ProductsResponse])

class Server(context: ActorContext[ServerCommand]) extends AbstractBehavior[ServerCommand](context){
  implicit val sys = context.system
  implicit val exec = sys.executionContext
  implicit val timeout = Timeout(10.seconds)

  val dbManagerActor: ActorRef[DbCommand] = context.spawn[DbCommand](DbManager(), "db-manager")
  val httpServer = context.spawn[ClientCommand](HttpServer(context.self), "http-server")
  var requestId = 1
  val requests = new mutable.HashMap[Int, OccurrencesRequest]()
  var opineoClientsId = 1

  override def onMessage(msg: ServerCommand): Behavior[ServerCommand] = {
    msg match {
      case SearchProduct(name, client) => search(name, client)
      case Occurrences(_, occurrences, requestId) => {
        requests.get(requestId) match {
          case Some(req) => req.client ! ProductsResponse(req.prices, occurrences)
          case None => println(s"No request for id $requestId")
        }
        Behaviors.same
      }
      case SearchProductReviews(query, client) => {
        val opineoClient = context.spawn[OpineoCommands](OpineoClient(), s"opineo-client-$opineoClientsId")
        opineoClientsId += 1
        opineoClient.ask[ProductsReviews](ref => ReviewQuery(query, ref)).onComplete{
          case Success(value) => client ! ReviewResponse(value.reviews)
          case Failure(exception) => println(exception)
        }

        Behaviors.same
      }
    }
  }

  private def search(name: String, client: ActorRef[ProductsResponse]): Behavior[ServerCommand] = {
    (1 to 2).map { i =>
      Source.single(i)
        .map({ _ =>
          Thread.sleep(Random.between(100, 501))
          Random.between(1, 11)
        }).async
        .completionTimeout(300.millis)
        .recover({_ => -1})
    }.reduce(_++_)
      .filter(_ != -1)
      .runWith(Sink.seq[Int])
      .onComplete({
        case Success(value) => {
          requests.addOne(requestId, new OccurrencesRequest(value, client))
          dbManagerActor ! AddAndReadQueryOccurrences(name, requestId, context.self)
          requestId += 1
        }
        case Failure(exception) => println(exception)
      })

    Behaviors.same
  }
}

object Server {
  def apply(): Behavior[ServerCommand] = Behaviors.setup[ServerCommand](ctx => new Server(ctx))
}

