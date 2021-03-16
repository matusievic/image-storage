package by.matusievic.imagestorage.sns

import by.matusievic.imagestorage.common.Implicits
import org.scalatra._

import scala.concurrent.ExecutionContext

class SnsServlet extends ScalatraServlet with FutureSupport {
  private val snsService: SnsService = SnsService()

  override protected implicit def executor: ExecutionContext = Implicits.ec

  post("/subscribe") {
    request.parameters.get("email").filter(_.nonEmpty) match {
      case Some(email) =>
        snsService.subscribe(email)
          .map(_ => Ok(<p>Success: Subscribtion notification send</p>))
          .recover(_ => InternalServerError(<p>Someting went wrong.</p>))
      case None =>
        BadRequest(<p>Hey! You forgot to supply an email.</p>)
    }
  }

  post("/unsubscribe") {
    request.parameters.get("email").filter(_.nonEmpty) match {
      case Some(email) =>
        snsService.unsubscribe(email)
          .map(_ => Ok(<p>Success: Unsubscribed</p>))
          .recover(_ => InternalServerError(<p>Someting went wrong.</p>))
      case None =>
        BadRequest(<p>Hey! You forgot to supply an email.</p>)
    }
  }
}
