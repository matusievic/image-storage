package by.matusievic.imagestorage.download

import by.matusievic.imagestorage.common.Implicits._
import by.matusievic.imagestorage.metadata.MetadataSearch
import by.matusievic.imagestorage.s3.S3Service

import scala.concurrent.Future

class FileDownloader {
  private val s3Service = S3Service()
  private val metadataSearch = MetadataSearch()

  def findRandom(): Future[DownloadResponse] = {
    for {
      key <- s3Service.randomKey
      bucketObject <- s3Service.find(key)
      metadata <- metadataSearch.findByName(key)
    } yield DownloadResponse(metadata, bucketObject)
  }

  def findByName(name: String): Future[DownloadResponse] = {
    for {
      bucketObject <- s3Service.find(name)
      metadata <- metadataSearch.findByName(name)
    } yield DownloadResponse(metadata, bucketObject)
  }
}

object FileDownloader {
  private val Instance = new FileDownloader()
  def apply(): FileDownloader = Instance
}
