package by.matusievic.imagestorage.upload

import by.matusievic.imagestorage.common.S3Bucket._
import com.amazonaws.services.s3.model.ObjectMetadata

class FileUploader {
  def upload(name: String, bytes: Array[Byte]): Unit = {
    bucket.putObject(name, bytes, new ObjectMetadata())
  }
}

object FileUploader {
  private val Instance = new FileUploader()
  def apply(): FileUploader = Instance
}
