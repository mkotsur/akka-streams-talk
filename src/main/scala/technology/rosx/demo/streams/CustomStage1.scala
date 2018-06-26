package technology.rosx.demo.streams

import java.io.{BufferedReader, InputStreamReader}

import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl.{Keep, RunnableGraph, Sink, Source}
import akka.stream.stage.{GraphStage, GraphStageLogic}
import akka.{Done, NotUsed, stream}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object CustomStage1 extends App {

  private implicit val actorSystem: ActorSystem = ActorSystem()
  private implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()

  val source: Source[Int, NotUsed] = Source(1 to 10)
  val flow = new KeypressFlow()
  val sink: Sink[Int, Future[Done]] = Sink.foreach((x: Int) => println(x))

  val graph: RunnableGraph[Future[Done]] = source.via(flow).toMat(sink)(Keep.right)

  val result: Future[Done] = graph.run()


  class KeypressFlow extends GraphStage[FlowShape[Int, Int]] {

    val out: Outlet[Int] = Outlet("NumbersSource.out")
    val in: Inlet[Int] = Inlet("NumbersSource.in")

    override val shape = stream.FlowShape(in, out)

    override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = {
      new GraphStageLogic(shape) {
        lazy val reader = new BufferedReader(new InputStreamReader(System.in))

        setHandler(out, () => { // onPull()
          println("Downstream has pulled")
          pull(in)
        })

        setHandler(in, () => { // onPush()
          println("Upstream pushed")
          val i = grab(in)
          println(s"Hit Enter to let element [$i] through...")
          reader.readLine()
          push(out, i)
        })

      }
    }
  }

  Await.result(result, Duration.Inf)
  System.exit(0)
}
