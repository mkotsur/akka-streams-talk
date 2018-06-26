package technology.rosx.demo.streams

import scala.concurrent.Future

class CoolDb {

  import scala.concurrent.ExecutionContext.Implicits.global

  def store(n: Long): Future[Unit] = Future {
    Thread.sleep(1000)
    println(s"Stored $n")
  }
}
