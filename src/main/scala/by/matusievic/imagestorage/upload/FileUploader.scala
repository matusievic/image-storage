package by.matusievic.imagestorage.upload

import by.matusievic.imagestorage.common.Implicits._
import by.matusievic.imagestorage.metadata.{ImageMetadata, MetadataRepository}
import by.matusievic.imagestorage.s3.S3Service
import by.matusievic.imagestorage.sns.SnsService

import java.time.LocalDateTime
import scala.concurrent.Future

class FileUploader {
  private val s3Service = S3Service()
  private val snsService = SnsService()
  private val metadataRepository = MetadataRepository()

  def upload(name: String, bytes: Array[Byte]): Future[ImageMetadata] = {
    val metadata = ImageMetadata(name, bytes.length, LocalDateTime.now())
    for {
      _ <- s3Service.put(name, bytes)
      _ <- metadataRepository.insertMetadata(metadata)
      _ <- snsService.publish(s"Image uploaded: $metadata")
    } yield metadata
  }
}

object FileUploader {
  private val Instance = new FileUploader()
  def apply(): FileUploader = Instance
}
