package by.matusievic.imagestorage.sns

import org.scalatra._

class SnsServlet extends ScalatraServlet {
  private val snsService: SnsService = SnsService()

  post("/subscribe") {
    request.parameters.get("email") match {
      case Some(email) =>
        snsService.subscribe(email)
        Ok(<p>Success: Subscribtion request sent</p>)
      case None =>
        BadRequest(<p>Hey! You forgot to suppluy a email.</p>)
    }
  }

  post("/unsubscribe") {
    request.parameters.get("email") match {
      case Some(email) =>
        snsService.unsubscribe(email)
        Ok(<p>Success: Unsubscribed</p>)
      case None =>
        BadRequest(<p>Hey! You forgot to suppluy a email.</p>)
    }
  }
}
