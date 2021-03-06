package by.matusievic.imagestorage.download

import awscala.s3._
import by.matusievic.imagestorage.common.S3Bucket._

import scala.util.Random

class FileDownloader {
  def findRandom(): Option[S3Object] = {
    val summaries = bucket.objectSummaries()
    val index = Random.nextInt(summaries.length)
    bucket.getObject(summaries(index).getKey)
  }

  def findByName(name: String): Option[S3Object] = {
    bucket.getObject(name)
  }

}

object FileDownloader {
  private val Instance = new FileDownloader()
  def apply(): FileDownloader = Instance
}