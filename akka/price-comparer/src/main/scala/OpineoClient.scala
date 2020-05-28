import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.actor.typed.scaladsl.adapter._
import akka.http.scaladsl.unmarshalling.Unmarshal
import org.jsoup.Jsoup

import scala.util.{Failure, Success}

sealed trait OpineoCommands
case class ReviewQuery(product: String, replyTo: ActorRef[ProductsReviews]) extends OpineoCommands

class OpineoClient(context: ActorContext[OpineoCommands]) extends AbstractBehavior[OpineoCommands](context) {
  implicit val actorSystem = context.system.toClassic
  implicit val executioncontext = context.executionContext

  override def onMessage(msg: OpineoCommands): Behavior[OpineoCommands] = {
    msg match {
      case ReviewQuery(product, replyTo) => {
        Http().singleRequest(HttpRequest(uri = s"https://www.opineo.pl/?szukaj=${product}&s=2"))
          .flatMap{ res => Unmarshal(res.entity).to[String] }
          .map{ res => Jsoup.parse(res).body().getElementsByClass("pl_attr").first().text() }
          .onComplete{
            case Success(value) => replyTo ! ProductsReviews(product, value)
            case Failure(exception) => println(exception)
          }
      }
    }
    Behaviors.stopped
  }
}

object OpineoClient {
  def apply(): Behavior[OpineoCommands] = Behaviors.setup(ctx => new OpineoClient(ctx))
}