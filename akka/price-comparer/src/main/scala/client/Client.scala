package client

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, Behavior}
import server.{SearchProduct, ServerCommand}

sealed trait ClientCommand
case class SearchCommand(productName: String) extends ClientCommand
case class ProductsResponse(prices: Seq[Int], occurrences: Int) extends ClientCommand
case class ReviewResponse(reviews: String) extends ClientCommand

class Client(server: ActorRef[ServerCommand], context: ActorContext[ClientCommand]) extends AbstractBehavior[ClientCommand](context){
  override def onMessage(msg: ClientCommand): Behavior[ClientCommand] = {
    msg match {
      case SearchCommand(productName: String) => 
        server ! SearchProduct(productName, context.self)
      case ProductsResponse(products: Seq[Int], occurrences: Int) =>
        println("Response received", products, occurrences)
    }
    Behaviors.same
  }
}

object Client {
  def apply(server: ActorRef[ServerCommand]): Behavior[ClientCommand] = Behaviors.setup[ClientCommand](ctx => new Client(server, ctx))
}

