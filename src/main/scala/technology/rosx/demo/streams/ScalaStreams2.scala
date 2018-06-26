package technology.rosx.demo.streams

import scala.concurrent.Future

object ScalaStreams2 extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  lazy val fibs: Stream[Long] = 0L #::
    1L #::
    fibs.zip(fibs.tail).map { n => n._1 + n._2 }

  fibs.foreach { n =>
    Future {
      Thread.sleep(1000)
      println(n)
    }
  }

}

