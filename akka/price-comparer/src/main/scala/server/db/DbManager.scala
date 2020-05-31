package server.db

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, Behavior}
import akka.stream.alpakka.slick.javadsl.SlickSession
import akka.stream.alpakka.slick.scaladsl.Slick
import akka.stream.scaladsl.{Sink, Source}
import server.Occurrences
import slick.jdbc.SQLiteProfile.api._

import scala.util.{Failure, Success}


sealed trait DbCommand
case class AddQueryOccurrence(query: String) extends DbCommand
case class ReadQueriesOccurrences(query: String, requestId: Int, replyTo: ActorRef[Occurrences]) extends DbCommand
case class AddAndReadQueryOccurrences(query: String, requestId: Int, replyTo: ActorRef[Occurrences]) extends DbCommand

class DbManager(context: ActorContext[DbCommand]) extends AbstractBehavior[DbCommand](context) {
  implicit val db: SlickSession = SlickSession.forConfig("queriesDb")
  implicit val system = context.system
  implicit val executionContext = context.executionContext

  override def onMessage(msg: DbCommand): Behavior[DbCommand] = {
    msg match {
      case AddQueryOccurrence(query) => {
        addOccurrence(query)
      }
      case ReadQueriesOccurrences(query, requestId, replyTo) => {
        readOccurrences(query).onComplete {
          case Success(occ) => returnOccurrences(query, occ, requestId, replyTo)
          case Failure(exception) => context.log.error(s"Cannot read: $exception")
        }
      }
      case AddAndReadQueryOccurrences(query, requestId, replyTo) => {
        addOccurrence(query).flatMap{ _ =>
          readOccurrences(query)
        }.onComplete {
          case Success(occ) => returnOccurrences(query, occ, requestId, replyTo)
          case Failure(exception) => context.log.error(s"Unable to complete command: $exception")
        }
      }
    }
    Behaviors.same
  }

  private def addOccurrence(query: String) = {
    Source.single(query)
      .runWith(
        Slick.sink(user => sqlu"INSERT OR REPLACE INTO queries(query, occurences) VALUES ($query, coalesce((SELECT occurences FROM queries WHERE query=$query), 0)+1);")
      )
  }

  private def readOccurrences(query: String) = {
    Slick.source(sql"SELECT occurences FROM queries WHERE query=$query".as[Int])
      .runWith(Sink.head)
  }

  private def returnOccurrences(query: String, occurrences: Int, requestId: Int, replyTo: ActorRef[Occurrences]) = {
    replyTo ! Occurrences(query, occurrences, requestId)
  }
}

object DbManager {
  def apply(): Behavior[DbCommand] = Behaviors.setup[DbCommand]{ ctx => new DbManager(ctx)}
}
