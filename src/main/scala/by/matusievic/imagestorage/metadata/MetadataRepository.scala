package by.matusievic.imagestorage.metadata

import DbConfig._
import doobie.implicits._

import scala.concurrent.Future

trait MetadataSearch {
  def findByName(name: String): Future[ImageMetadata]
}

object MetadataSearch {
  def apply(): MetadataSearch = MetadataRepository()
}


trait MetadataRepository extends MetadataSearch {
  def insertMetadata(metadata: ImageMetadata): Future[Int]
}

object MetadataRepository {
  private val instance = new MetadataRepositoryImpl()
  def apply(): MetadataRepository = instance
}


class MetadataRepositoryImpl extends MetadataRepository {
  override def findByName(name: String): Future[ImageMetadata] = {
    sql"SELECT * FROM image_metadata WHERE name = $name"
      .query[ImageMetadata]
      .unique
      .transact(aux)
      .unsafeToFuture
  }

  override def insertMetadata(metadata: ImageMetadata): Future[Int] = {
    sql"""
    INSERT INTO image_metadata (name, size_in_bytes, upload_date)
    VALUES (${metadata.name}, ${metadata.sizeInBytes}, ${metadata.uploadDate.toString})
    """.update.run.transact(aux).unsafeToFuture
  }

  private def init() = {
    sql"""
    CREATE TABLE IF NOT EXISTS image_metadata (
      name VARCHAR(256) NOT NULL UNIQUE,
      size_in_bytes INT,
      upload_date VARCHAR(256)
    );
    """.update.run.transact(aux).unsafeRunSync
  }

  init()
}
