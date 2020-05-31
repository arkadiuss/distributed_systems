package server

import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, Behavior}
import akka.stream.scaladsl.{Sink, Source}
import akka.util.Timeout
import client.{ClientCommand, ProductsResponse, ReviewResponse}
import server.db.{AddAndReadQueryOccurrences, DbCommand, DbManager}
import server.external.{OpineoClient, OpineoCommands, ReviewQuery}

import scala.Option
import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Random, Success}

sealed trait ServerCommand
case class SearchProduct(name: String, client: ActorRef[ProductsResponse]) extends ServerCommand
case class Occurrences(query: String, occurrences: Int, requestId: Int) extends ServerCommand
case class SearchProductReviews(query: String, client: ActorRef[ReviewResponse]) extends ServerCommand
case class ProductsReviews(query: String, reviews: String) extends ServerCommand

class Server(context: ActorContext[ServerCommand]) extends AbstractBehavior[ServerCommand](context){
  implicit val sys = context.system
  implicit val exec = sys.executionContext
  implicit val timeout = Timeout(10.seconds)

  val dbManagerActor: ActorRef[DbCommand] = context.spawn[DbCommand](DbManager(), "db-manager")
  val httpServer = context.spawn[ClientCommand](HttpServerApi(context.self), "http-server")
  var opineoClientsId = 1
  var requestId = 1
  val requests = mutable.HashMap[Int, Int]()

  override def onMessage(msg: ServerCommand): Behavior[ServerCommand] = {
    msg match {
      case SearchProduct(name, client) => search(name, client)
      case Occurrences(_, occurrences, requestId) => {
        requests.addOne(requestId, occurrences)
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
    val contextRequestId = requestId
    requestId += 1
    dbManagerActor ! AddAndReadQueryOccurrences(name, contextRequestId, context.self)

    val pricesTask = (1 to 2).map { i =>
      val t = Random.between(100, 501)
      Source.single(i)
//        .map({_ => Thread.sleep(t); t}).async
        .map(_ => Random.between(1, 11))
        .delay(t.millis)
        .completionTimeout(300.millis)
        .recover({_ => -1})
    }.reduce(_++_)
      .filter(_ != -1)
      .runWith(Sink.seq[Int])

    pricesTask
      .onComplete({
        case Success(value) => {
          var occurrences = -1
          requests.get(contextRequestId) match {
            case Some(value) => occurrences = value
            case None => occurrences = -1
          }
          client ! ProductsResponse(value, occurrences)
        }
        case Failure(exception) => println(exception)
      })

    Behaviors.same
  }
}

object Server {
  def apply(): Behavior[ServerCommand] = Behaviors.setup[ServerCommand](ctx => new Server(ctx))
}

