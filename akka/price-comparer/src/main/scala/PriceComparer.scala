import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import com.typesafe.config.ConfigFactory

import scala.io.StdIn.{readInt, readLine}
import scala.util.{Failure, Success, Try}

sealed trait PriceComparerSystem
case class InputCommand(input: String) extends PriceComparerSystem

object PriceComparer {
  def apply(clientsNum: Int): Behavior[PriceComparerSystem] = Behaviors.setup[PriceComparerSystem]{ ctx: ActorContext[PriceComparerSystem] =>
    val server = ctx.spawn[ServerCommand](Server(), "server")
    val clients = (1 to clientsNum).map { i => ctx.spawn[ClientCommand](Client(server), "client"+i) }

    Behaviors.receive[PriceComparerSystem] { (_, msg) =>
      msg match {
        case InputCommand(input: String) => {
          val inputValues = input.split(" ")
          val (clientIndex, productName) = (inputValues(0).toInt, inputValues(1))

          Try(clients(clientIndex)) match {
            case Success(client) => { client ! SearchCommand(productName) }
            case Failure(_: ArrayIndexOutOfBoundsException) => println("No such client")
            case Failure(exception) => println("Unknown exception: {}", exception)
          }

          Behaviors.same
        }
      }
    }
  }
}

object PriceComparerApp {
  def main(args: Array[String]): Unit = {
    val clients = readInt()
    val conf = ConfigFactory.load("server.conf")
    val systemRef = ActorSystem[PriceComparerSystem](PriceComparer(clients), "price-comparer-system", conf)
    while(true) {
      val line = readLine()
      systemRef ! InputCommand(line)
    }
  }
}

