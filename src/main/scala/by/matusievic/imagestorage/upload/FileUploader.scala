package by.matusievic.imagestorage.upload

import by.matusievic.imagestorage.common.S3Bucket._
import by.matusievic.imagestorage.metadata.{ImageMetadata, MetadataRepository}
import com.amazonaws.services.s3.model.ObjectMetadata

import java.time.LocalDateTime

class FileUploader {
  private val metadataRepository = MetadataRepository()

  def upload(name: String, bytes: Array[Byte]): ImageMetadata = {
    bucket.putObject(name, bytes, new ObjectMetadata())
    val metadata = ImageMetadata(name, bytes.length, LocalDateTime.now())
    metadataRepository.insertMetadata(metadata)
    metadata
  }
}

object FileUploader {
  private val Instance = new FileUploader()
  def apply(): FileUploader = Instance
}
