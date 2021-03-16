package by.matusievic.imagestorage.s3

import akka.stream.alpakka.s3.MultipartUploadResult
import akka.stream.alpakka.s3.scaladsl.S3
import akka.stream.scaladsl._
import akka.util.ByteString
import by.matusievic.imagestorage.common.Implicits._
import by.matusievic.imagestorage.s3.S3BucketConfig._

import scala.concurrent.Future
import scala.util.Random

trait S3Service {
  def put(key: String, bytes: Array[Byte]): Future[MultipartUploadResult]

  def find(key: String): Future[BucketObject]

  def randomKey: Future[String]
}

class S3ServiceImpl extends S3Service {
  override def put(key: String, bytes: Array[Byte]): Future[MultipartUploadResult] = {
    Source
      .single(ByteString(bytes))
      .runWith(S3.multipartUpload(BucketName, key))
  }

  override def find(key: String): Future[BucketObject] = {
    S3
      .download(BucketName, key)
      .runWith(Sink.head)
      .flatMap {
        case Some((content, metadata)) =>
          for {
            contentType <- Future(metadata.contentType.get)
            bytes <- content.runWith(Sink.head[ByteString])
          } yield BucketObject(bytes.toArray, contentType)
      }
  }

  override def randomKey: Future[String] = {
    S3
      .listBucket(BucketName, None)
      .runWith(Sink.seq)
      .filter(_.nonEmpty)
      .map(s => s(Random.nextInt(s.length)).getKey)
  }
}

object S3Service {
  private val instance = new S3ServiceImpl()
  def apply(): S3Service = instance
}
