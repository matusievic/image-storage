package by.matusievic.imagestorage.upload

import by.matusievic.imagestorage.common.Implicits
import org.scalatra._
import org.scalatra.servlet.{FileUploadSupport, MultipartConfig}

import scala.concurrent.ExecutionContext

class ImageUploadServlet extends ScalatraServlet with FileUploadSupport with FutureSupport {
  configureMultipartHandling(MultipartConfig(maxFileSize = Some(3 * 1024 * 1024)))

  private val fileUploader: FileUploader = FileUploader()

  override protected implicit def executor: ExecutionContext = Implicits.ec

  post("/") {
    fileParams.get("file") match {
      case Some(f) =>
        fileUploader.upload(f.name, f.get())
          .map(metadata => Ok(<p>Success: {metadata}</p>))
          .recover(_ => BadRequest(<p>Something went wrong.</p>))
      case None =>
        BadRequest(<p>Hey! You forgot to select a file.</p>)
    }
  }
}
