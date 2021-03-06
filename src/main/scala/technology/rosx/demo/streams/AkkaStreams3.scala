package technology.rosx.demo.streams

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Keep, RunnableGraph, Sink, Source}
import akka.{Done, NotUsed}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object AkkaStreams3 extends App {

  val source: Source[Int, NotUsed] = Source(1 to 10)

  val sink: Sink[String, Future[Done]] = Sink.foreach((x: String) => println(x))

  val flow: Flow[Int, String, NotUsed] = Flow.fromFunction(x => s"$x * 2 = ${x * 2}")

  val graph: RunnableGraph[Future[Done]] = source.viaMat(flow)(Keep.right).toMat(sink)(Keep.right)

  implicit val actorSystem = ActorSystem()
  implicit val actorMaterializer = ActorMaterializer()
  val result: Future[Done] = graph.run()

  Await.ready(result, Duration.Inf)

}
