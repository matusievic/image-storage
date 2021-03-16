package by.matusievic.imagestorage.s3

import awscala.Region
import awscala.s3.{Bucket, S3}

object S3BucketConfig {
  implicit val s3: S3 = S3.at(Region.Frankfurt)

  private val BucketName = "andrei-matusevich-image-storage"
  val bucket: Bucket = s3.bucket(BucketName).getOrElse(s3.createBucket(BucketName))
}