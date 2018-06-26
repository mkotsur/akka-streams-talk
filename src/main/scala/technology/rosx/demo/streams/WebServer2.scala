package technology.rosx.demo.streams

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.Future
import scala.io.StdIn
import scala.util.Random

object WebServer2 {

  def main(args: Array[String]) {

    import akka.actor.ActorSystem
    import akka.http.scaladsl.Http
    import akka.stream.ActorMaterializer
    import akka.stream.scaladsl._

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val serverSource: Source[Http.IncomingConnection, Future[Http.ServerBinding]] =
      Http().bind(interface = "localhost", port = 8080)
      serverSource.to(Sink.foreach { connection => // foreach materializes the source
        println("Accepted new connection from " + connection.remoteAddress)
        // ... and then actually handle the connection
      }).run()
  }
}