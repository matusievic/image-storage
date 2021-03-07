package by.matusievic.imagestorage.upload

import org.scalatra._
import org.scalatra.servlet.{FileUploadSupport, MultipartConfig}

class ImageUploadServlet extends ScalatraServlet with FileUploadSupport {
  configureMultipartHandling(MultipartConfig(maxFileSize = Some(3 * 1024 * 1024)))

  private val fileUploader: FileUploader = FileUploader()

  post("/") {
    fileParams.get("file").filter(_.getSize > 0) match {
      case Some(file) =>
        val metadata = fileUploader.upload(file.name, file.get())
        Ok(<p>Success: {metadata}</p>)
      case None =>
        BadRequest(<p>Hey! You forgot to select a file.</p>)
    }
  }
}
