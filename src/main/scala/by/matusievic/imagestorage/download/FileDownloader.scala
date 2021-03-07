package by.matusievic.imagestorage.download

import by.matusievic.imagestorage.common.S3Bucket._
import by.matusievic.imagestorage.metadata.MetadataSearch

import scala.util.Random

class FileDownloader {
  private val metadataSearch = MetadataSearch()

  def findRandom(): Option[DownloadResponse] = {
    val summaries = bucket.objectSummaries()
    val maybeIndex = Option.when(summaries.nonEmpty)(Random.nextInt(summaries.length))
    val maybeKey = maybeIndex.map(summaries(_).getKey)
    for {
      key <- maybeKey
      s3object <- bucket.getObject(key)
      metadata <- metadataSearch.findByName(key)
    } yield DownloadResponse(metadata, s3object)
  }

  def findByName(name: String): Option[DownloadResponse] = {
    for {
      s3object <- bucket.getObject(name)
      metadata <- metadataSearch.findByName(name)
    } yield DownloadResponse(metadata, s3object)
  }
}

object FileDownloader {
  private val Instance = new FileDownloader()
  def apply(): FileDownloader = Instance
}
