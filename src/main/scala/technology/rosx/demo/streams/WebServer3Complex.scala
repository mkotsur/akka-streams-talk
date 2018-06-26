package technology.rosx.demo.streams

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.{IncomingConnection, ServerBinding}
import akka.http.scaladsl.model.HttpResponse
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Broadcast, Flow, Sink, Source}

import scala.concurrent.Future

object WebServer3Complex extends App {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val workers: Source[IncomingConnection, Future[ServerBinding]] =
    Http().bind(interface = "localhost", port = 8080)

  val distributedJobs: Source[IncomingConnection, Future[ServerBinding]] =
    Http().bind(interface = "localhost", port = 8888)


  workers.zip(distributedJobs).runForeach {
    case (connection, jobConnection) =>
      println(s"Job ${jobConnection.hashCode()} goes to ${connection.remoteAddress}")
      connection.handleWith(Flow.fromFunction(_ => HttpResponse()))
  }

}
