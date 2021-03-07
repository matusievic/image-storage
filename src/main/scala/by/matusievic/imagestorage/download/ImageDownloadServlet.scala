package by.matusievic.imagestorage.download

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
    request.parameters.get("name").filter(_.nonEmpty).flatMap(fileDownloader.findByName) match {
      case Some(file) => fileRoResponse(file)
      case None => NotFound("Image Not Found")
    }
  }

  private def fileRoResponse(res: DownloadResponse) = Ok(
    body = IOUtils.toByteArray(res.s3Object.content),
    headers = Map(
      ContentType -> res.s3Object.getObjectMetadata.getContentType,
      ContentDisposition -> s"attachment; filename=${res.metadata.uploadDate}_${res.metadata.name}"
    )
  )
}
