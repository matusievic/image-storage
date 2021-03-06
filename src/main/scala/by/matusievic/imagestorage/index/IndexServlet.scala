package by.matusievic.imagestorage.index

import by.matusievic.imagestorage.common.Template
import org.scalatra.ScalatraServlet

class IndexServlet extends ScalatraServlet {
  get("/") {
    Template.page(
      title = "Image Storage",
      content = Seq(
        <form action={url("/upload")} method="post" enctype="multipart/form-data">
          <p>File to upload:
            <input type="file" name="file"/>
            <input type="submit" class="btn btn-primary" value="Upload"/>
          </p>
        </form>,
        <form action={url("/download")} method="get" enctype="multipart/form-data">
          <p>File to download:
            <input type="text" name="name"/>
            <input type="submit" class="btn btn-primary" value="Download"/>
          </p>
        </form>,
        <form action={url("/download/random")} method="get" enctype="multipart/form-data">
          <p>
            <input type="submit" class="btn btn-primary" value="Random"/>
          </p>
        </form>,
      )
    )
  }
}
