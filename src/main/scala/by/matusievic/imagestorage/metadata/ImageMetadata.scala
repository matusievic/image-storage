package by.matusievic.imagestorage.metadata

import doobie.{Read, Write}

import java.time.LocalDateTime

case class ImageMetadata(name: String, sizeInBytes: Int, uploadDate: LocalDateTime)

object ImageMetadata {
  implicit val read: Read[ImageMetadata] = Read[(String, Int, String)].map {
    case (name: String, size: Int, date: String) => ImageMetadata(name, size, LocalDateTime.parse(date))
  }
  implicit val write: Write[ImageMetadata] = Write[(String, Int, String)].contramap {
    case ImageMetadata(name, size, date) => (name, size, date.toString)
  }
}
