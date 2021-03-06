import by.matusievic.imagestorage.download.ImageDownloadServlet
import by.matusievic.imagestorage.index.IndexServlet
import by.matusievic.imagestorage.upload.ImageUploadServlet
import org.scalatra._

import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new IndexServlet, "/")
    context.mount(new ImageUploadServlet, "/upload")
    context.mount(new ImageDownloadServlet, "/download")
  }
}
