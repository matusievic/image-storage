package by.matusievic.imagestorage.download

import by.matusievic.imagestorage.metadata.ImageMetadata
import by.matusievic.imagestorage.s3.BucketObject

case class DownloadResponse(metadata: ImageMetadata, bucketObject: BucketObject)
