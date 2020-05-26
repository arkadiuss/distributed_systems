import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

sealed trait ClientCommand
case class SearchCommand(productName: String) extends ClientCommand
case class ProductsResponse(products: Seq[Int]) extends ClientCommand

class Client(server: ActorRef[ServerCommand], context: ActorContext[ClientCommand]) extends AbstractBehavior[ClientCommand](context){
  override def onMessage(msg: ClientCommand): Behavior[ClientCommand] = {
    msg match {
      case SearchCommand(productName: String) => 
        server ! SearchProduct(productName, context.self)
      case ProductsResponse(products: Seq[Int]) =>
        println("Response received", products)
    }
    Behaviors.same
  }
}

object Client {
  def apply(server: ActorRef[ServerCommand]): Behavior[ClientCommand] = Behaviors.setup[ClientCommand](ctx => new Client(server, ctx))
}

