package technology.rosx.demo.streams

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.alpakka.s3.scaladsl.{MultipartUploadResult, S3Client}
import akka.stream.scaladsl.{Flow, Keep, RunnableGraph, Sink, Source}
import akka.util.ByteString
import akka.{Done, NotUsed}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object S3MultipartUpload1 extends App {

  private implicit val actorSystem = ActorSystem()
  private implicit val actorMaterializer = ActorMaterializer()

  val source: Source[Int, NotUsed] = Source(1 to 96)
  val s3Client = S3Client()

  private val sink: Sink[ByteString, Future[MultipartUploadResult]] = s3Client.multipartUpload("rosx-gunter-test-tasks", "test.file")

  private val eventualResult: Future[MultipartUploadResult] =
    source.via(Flow.fromFunction(int => ByteString(s"$int\n".getBytes))).runWith(sink)

  val result = Await.result(eventualResult, Duration.Inf)

  println("DONE", result)

  actorMaterializer.shutdown()
  actorSystem.terminate()
}
