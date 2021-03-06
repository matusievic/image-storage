package by.matusievic.imagestorage.common

import scala.xml.{Elem, Node}

object Template {
  def page(title: String, content: Seq[Node], defaultScripts: Seq[String] = Seq("/assets/js/jquery.min.js", "/assets/js/bootstrap.min.js")): Elem = {
    <html lang="en">
      <head>
        <title>
          {title}
        </title>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta name="description" content=""/>
        <meta name="author" content=""/>

        <link href="/assets/css/bootstrap.css" rel="stylesheet"/>
        <link href="/assets/css/bootstrap-responsive.css" rel="stylesheet"/>
        <link href="/assets/css/syntax.css" rel="stylesheet"/>
        <link href="/assets/css/scalatra.css" rel="stylesheet"/>

      </head>

      <body>
        <div class="navbar navbar-inverse navbar-fixed-top">
          <div class="navbar-inner">
            <div class="container">
              <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </a>
              <a class="brand" href="/">Scalatra Examples</a>
              <div class="nav-collapse collapse">

              </div>
            </div>
          </div>
        </div>

        <div class="container">
          <div class="content">
            <div class="page-header">
              <h1>
                {title}
              </h1>
            </div>{content}<hr/>
          </div>
        </div>{defaultScripts map { pth =>
        <script type="text/javascript" src={pth}></script>
      }}

      </body>

    </html>
  }
}
