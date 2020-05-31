import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import client.{Client, ClientCommand, SearchCommand}
import com.typesafe.config.ConfigFactory
import server.{Server, ServerCommand}

import scala.io.StdIn.{readInt, readLine}
import scala.util.{Failure, Success, Try}

sealed trait PriceComparerSystem
case class InputCommand(input: String) extends PriceComparerSystem

object PriceComparer {
  def apply(clientsNum: Int): Behavior[PriceComparerSystem] = Behaviors.setup[PriceComparerSystem]{ ctx: ActorContext[PriceComparerSystem] =>
    val server = ctx.spawn[ServerCommand](Server(), "server")
    val clients = (1 to clientsNum).map { i => ctx.spawn[ClientCommand](Client(server), "client"+i) }
    var clientI = 0
    Behaviors.receive[PriceComparerSystem] { (_, msg) =>
      msg match {
        case InputCommand(input: String) => {
          Try(clients(clientI)) match {
            case Success(client) => { client ! SearchCommand(input) }
            case Failure(_: ArrayIndexOutOfBoundsException) => println("No such client")
            case Failure(exception) => println("Unknown exception: {}", exception)
          }
          clientI = (clientI+1)%clientsNum

          Behaviors.same
        }
      }
    }
  }
}

object PriceComparerApp {
  def main(args: Array[String]): Unit = {
    val clientsPool = 10;
    val conf = ConfigFactory.load("server.conf")
    val systemRef = ActorSystem[PriceComparerSystem](PriceComparer(clientsPool), "price-comparer-system", conf)
    while(true) {
      val line = readLine()
      if(line.startsWith("repeated")) {
        val productStart = line.indexOf(" ", 9)
        val count = line.substring(9, productStart).toInt
        val product = line.substring(productStart + 1)
        for (_ <- 1 to count) {
          systemRef ! InputCommand(product)
        }
      } else {
        systemRef ! InputCommand(line)
      }
//      systemRef ! InputCommand("1 test"+Random.between(1,22))
//      Thread.sleep(Random.between(10,50))
    }
  }
}

