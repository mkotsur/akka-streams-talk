package technology.rosx.demo.streams

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, RunnableGraph, Sink, Source}
import akka.{Done, NotUsed}

import scala.concurrent.Future

object AkkaStreams2 extends App {

  implicit val actorSystem = ActorSystem()
  implicit val actorMaterializer = ActorMaterializer()

  val source: Source[Int, NotUsed] = Source(1 to 10)

  val sink: Sink[String, Future[Done]] = Sink.foreach((x: String) => println(x))

  val flow: Flow[Int, String, NotUsed] = Flow.fromFunction(x => s"$x * 2 = ${x * 2}")

  val graph: RunnableGraph[NotUsed] = source.via(flow).to(sink)

  val result: NotUsed = graph.run()
}
