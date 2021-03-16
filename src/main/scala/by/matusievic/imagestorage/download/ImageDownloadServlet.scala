package by.matusievic.imagestorage.download

import by.matusievic.imagestorage.common.Header._
import by.matusievic.imagestorage.common.Implicits
import org.scalatra._

import scala.concurrent.ExecutionContext

class ImageDownloadServlet extends ScalatraServlet with FutureSupport {
  private val fileDownloader: FileDownloader = FileDownloader()

  override protected implicit def executor: ExecutionContext = Implicits.ec

  get("/random") {
    fileDownloader.findRandom()
      .map(fileToResponse)
      .recover(_ => NotFound("Image Not Found"))
  }

  get("/") {
    request.parameters.get("name") match {
      case Some(name) =>
        fileDownloader.findByName(name)
          .map(fileToResponse)
          .recover(_ => NotFound("Image Not Found"))
      case None =>
        BadRequest(<p>Hey! You forgot to supply an email.</p>)
    }
  }

  private def fileToResponse(res: DownloadResponse) = Ok(
    body = res.bucketObject.content,
    headers = Map(
      ContentType -> res.bucketObject.contentType,
      ContentDisposition -> s"attachment; filename=${res.metadata.uploadDate}_${res.metadata.name}"
    )
  )
}
