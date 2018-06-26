package technology.rosx.demo.streams

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.{Done, NotUsed}
import akka.stream.scaladsl.{Flow, RunnableGraph, Sink, Source}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object AkkaStreams1 extends App {

  val source: Source[Int, NotUsed] = Source(1 to 10)

  val sink: Sink[String, Future[Done]] = Sink.foreach((x: String) => {
    Thread.sleep(100)
    println(x)
  })

  val flow: Flow[Int, String, NotUsed] = Flow.fromFunction(x => s"$x * 2 = ${x * 2}")

  // Option1
  val graph: RunnableGraph[NotUsed] = source.via(flow).to(sink)

  implicit val actorSystem = ActorSystem()
  implicit val actorMaterializer = ActorMaterializer()
  val result: NotUsed = graph.run()

//  Await.ready(actorSystem.terminate(), Duration.Inf)
  System.exit(0)
}
