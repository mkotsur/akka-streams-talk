package technology.rosx.demo.streams

import java.io.{BufferedReader, InputStreamReader}
import java.util.Date

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

object Ticks extends App  {


  implicit val actorSystem = ActorSystem()
  implicit val actorMaterializer = ActorMaterializer()


  import scala.concurrent.duration._
  Source.tick(1 second, 1 second, new Date())
    .via(Flow.fromFunction(t => {
      println("VIA")
      t
    })).to(Sink.foreach(t => {
    val reader = new BufferedReader(new InputStreamReader(System.in))
    println(s"Element $t is in the sink")
    println(s"Hit Enter...")
    reader.readLine()
  })).run()


}
