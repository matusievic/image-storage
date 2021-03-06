package by.matusievic.imagestorage.upload

import org.scalatra._
import org.scalatra.servlet.{FileUploadSupport, MultipartConfig}


class ImageUploadServlet extends ScalatraServlet with FileUploadSupport {
  configureMultipartHandling(MultipartConfig(maxFileSize = Some(3 * 1024 * 1024)))

  private val fileUploader: FileUploader = FileUploader()

  post("/") {
    fileParams.get("file") match {
      case Some(file) =>
        fileUploader.upload(file.name, file.get())
        Ok(<p>Success</p>)
      case None =>
        BadRequest(<p>Hey! You forgot to select a file.</p>)
    }
  }
}