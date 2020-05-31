package server

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.actor.typed.scaladsl.adapter._
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.util.Timeout
import akka.actor.typed.scaladsl.AskPattern._
import client.{ClientCommand, ProductsResponse, ReviewResponse}
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object HttpServerApi {
  def apply(server: ActorRef[ServerCommand]): Behavior[ClientCommand] = Behaviors.setup[ClientCommand] { ctx: ActorContext[ClientCommand] =>
    implicit val executionContext = ctx.executionContext
    implicit val classicSystem: akka.actor.ActorSystem = ctx.system.toClassic
    implicit val responseTimeout = Timeout(10.second)

    val priceRoute = path("price" / Segment) { product =>
      get {
        val f = server.ask[ProductsResponse](ref => SearchProduct(product, ref))(responseTimeout, ctx.system.scheduler)

        onComplete(f) {
          case Success(ProductsResponse(prices, occurrences)) =>
            complete(HttpEntity(ContentTypes.`application/json`, s"""{ "prices": "${prices.mkString(",")}", "occurrences": $occurrences }"""))
          case Failure(ex) =>
            complete(HttpResponse(StatusCodes.InternalServerError, entity = ex.getMessage))
        }
      }
    }

    val reviewRoute = path("review" / Segment) { product =>
      get {
        val f = server.ask[ReviewResponse](ref => SearchProductReviews(product, ref))(responseTimeout, ctx.system.scheduler)

        onComplete(f) {
          case Success(ReviewResponse(review)) =>
            complete(HttpEntity(ContentTypes.`application/json`, s"""{ "review": "${review}" }"""))
          case Failure(ex) =>
            complete(HttpResponse(StatusCodes.InternalServerError, entity = ex.getMessage))
        }
      }
    }

    val routes = priceRoute ~ reviewRoute

    Http().bindAndHandle(routes, "localhost", 8081)
    Behaviors.empty
  }
}