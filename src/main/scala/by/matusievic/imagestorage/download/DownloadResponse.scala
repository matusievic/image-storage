package by.matusievic.imagestorage.download

import awscala.s3.S3Object
import by.matusievic.imagestorage.metadata.ImageMetadata

case class DownloadResponse(metadata: ImageMetadata, s3Object: S3Object)
