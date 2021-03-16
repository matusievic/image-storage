package by.matusievic.imagestorage.s3

import by.matusievic.imagestorage.s3.S3BucketConfig._
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.util.IOUtils

import scala.util.Random

trait S3Service {
  def put(key: String, bytes: Array[Byte]): Unit

  def find(key: String): Option[BucketObject]

  def randomKey: Option[String]
}

class S3ServiceImpl extends S3Service {
  override def put(key: String, bytes: Array[Byte]): Unit = {
    bucket.putObject(key, bytes, new ObjectMetadata())
  }

  override def find(key: String): Option[BucketObject] = {
    bucket.getObject(key).map(o => BucketObject(IOUtils.toByteArray(o.content), o.metadata.getContentType))
  }

  override def randomKey: Option[String] = {
    val summaries = bucket.objectSummaries()
    Option.when(summaries.nonEmpty)(Random.nextInt(summaries.length)).map(summaries(_).getKey)
  }
}

object S3Service {
  private val instance = new S3ServiceImpl()
  def apply(): S3Service = instance
}
