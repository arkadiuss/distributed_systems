import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import akka.stream.scaladsl.{Merge, Sink, Source}

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Random, Success}

sealed trait ServerCommand
case class SearchProduct(name: String, client: ActorRef[ProductsResponse]) extends ServerCommand

class Server(context: ActorContext[ServerCommand]) extends AbstractBehavior[ServerCommand](context){
  implicit val sys = context.system
  implicit val exec = sys.executionContext
  override def onMessage(msg: ServerCommand): Behavior[ServerCommand] = {
    msg match {
      case SearchProduct(name, client) => search(name, client)
    }
  }

  private def search(name: String, client: ActorRef[ProductsResponse]): Behavior[ServerCommand] = {
    val sources = (1 to 2).map { i =>
      Source.single(i)
        .map({ _ =>
          val sl = Random.between(100, 501)
          Thread.sleep(sl)
          Random.between(1, 11)
        }).async.completionTimeout(300.millis).recover({ex => -1})
    }
    val res = Source.combine(
      sources(0),
      sources(1)
    )(Merge(_))
      .filter(_ != -1)
      .runWith(Sink.seq[Int])

    res.andThen({
      case Success(value) => client ! ProductsResponse(value)
      case Failure(exception) => println(exception)
    })

    Behaviors.same
  }
}

object Server {
  def apply(): Behavior[ServerCommand] = Behaviors.setup[ServerCommand](ctx => new Server(ctx))
}

