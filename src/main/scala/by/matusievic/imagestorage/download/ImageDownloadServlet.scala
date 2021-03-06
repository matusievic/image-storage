package by.matusievic.imagestorage.download

import awscala.s3.S3Object
import by.matusievic.imagestorage.common.Header._
import com.amazonaws.util.IOUtils
import org.scalatra._

class ImageDownloadServlet extends ScalatraServlet {
  private val fileDownloader: FileDownloader = FileDownloader()

  get("/random") {
    fileDownloader.findRandom() match {
      case Some(file) => fileRoResponse(file)
      case None => NotFound("Image Not Found")
    }
  }

  get("/") {
    request.parameters.get("name").flatMap(fileDownloader.findByName) match {
      case Some(file) => fileRoResponse(file)
      case None => NotFound("Image Not Found")
    }
  }

  private def fileRoResponse(file: S3Object) = Ok(
    body = IOUtils.toByteArray(file.content),
    headers = Map(
      ContentType -> file.getObjectMetadata.getContentType,
      ContentDisposition -> ("attachment; filename=" + file.getKey)
    )
  )
}