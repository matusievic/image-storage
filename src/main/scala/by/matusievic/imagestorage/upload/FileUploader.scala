package by.matusievic.imagestorage.upload

import by.matusievic.imagestorage.metadata.{ImageMetadata, MetadataRepository}
import by.matusievic.imagestorage.s3.S3Service
import by.matusievic.imagestorage.sns.SnsService

import java.time.LocalDateTime

class FileUploader {
  private val s3Service = S3Service()
  private val snsService = SnsService()
  private val metadataRepository = MetadataRepository()

  def upload(name: String, bytes: Array[Byte]): ImageMetadata = {
    s3Service.put(name, bytes)
    val metadata = ImageMetadata(name, bytes.length, LocalDateTime.now())
    metadataRepository.insertMetadata(metadata)
    snsService.publish(s"Image uploaded: $metadata")
    metadata
  }
}

object FileUploader {
  private val Instance = new FileUploader()
  def apply(): FileUploader = Instance
}
