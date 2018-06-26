package technology.rosx.demo.streams

object ScalaStreams1 extends App {


  private def runSyncronously(): Unit = {
    lazy val fibs: Stream[Long] = 0L #::
      1L #::
      fibs.zip(fibs.tail).map { n => n._1 + n._2 }

    fibs.foreach { n =>
      Thread.sleep(1000)
      println(n)
    }
  }

  private def runAsyncronously(): Unit = {
    lazy val fibs: Stream[Long] = 0L #::
      1L #::
      fibs.zip(fibs.tail).map { n => n._1 + n._2 }

    val db = new CoolDb
    fibs.foreach { n =>
      db.store(n)
    }
  }


  runAsyncronously()


}

