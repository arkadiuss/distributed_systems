import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.actor.typed.scaladsl.adapter._

object HttpServer {
  def apply() = Behaviors.setup[Nothing] { ctx: ActorContext[Nothing] =>
    implicit val executionContext = ctx.executionContext
    implicit val classicSystem: akka.actor.ActorSystem = ctx.system.toClassic

    val route = path("price" / Segment) { product =>
        get {
          complete(HttpEntity(ContentTypes.`application/json`, "{ \""+product+"\": \"ok\"}"))
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    Behaviors.empty
  }
}