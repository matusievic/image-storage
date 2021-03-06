package by.matusievic.imagestorage.common

import cats.effect.{ContextShift, IO}
import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor
import doobie.util.transactor.Transactor.Aux

object Db {
  private val host = sys.env.getOrElse("DB_HOST", "UNKNOWN")
  private val database = sys.env.getOrElse("DB_NAME", "UNKNOWN")
  private val port = sys.env.getOrElse("DB_PORT", "UNKNOWN")
  private val user: String = sys.env.getOrElse("DB_USER", "UNKNOWN")
  private val password: String = sys.env.getOrElse("DB_PASSWORD", "UNKNOWN")

  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContexts.synchronous)
  val aux: Aux[IO, Unit] = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    s"jdbc:postgresql://$host:$port/$database",
    user,
    password
  )
}
