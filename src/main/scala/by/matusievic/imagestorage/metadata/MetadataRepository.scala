package by.matusievic.imagestorage.metadata

import by.matusievic.imagestorage.common.Db._
import doobie.implicits._

trait MetadataSearch {
  def findByName(name: String): Option[ImageMetadata]
}

object MetadataSearch {
  def apply(): MetadataSearch = MetadataRepository()
}


trait MetadataRepository extends MetadataSearch {
  def insertMetadata(metadata: ImageMetadata): Unit
}

object MetadataRepository {
  private val instance = new MetadataRepositoryImpl()
  def apply(): MetadataRepository = instance
}


class MetadataRepositoryImpl extends MetadataRepository {
  override def findByName(name: String): Option[ImageMetadata] = {
    sql"SELECT * FROM image_metadata WHERE name = $name"
      .query[ImageMetadata]
      .option
      .transact(aux)
      .unsafeRunSync
  }

  override def insertMetadata(metadata: ImageMetadata): Unit = {
    sql"""
    INSERT INTO image_metadata (name, size_in_bytes, upload_date)
    VALUES (${metadata.name}, ${metadata.sizeInBytes}, ${metadata.uploadDate.toString})
    """.update.run.transact(aux).unsafeRunSync
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
