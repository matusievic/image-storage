package by.matusievic.imagestorage.lamda

import akka.Done
import akka.stream.alpakka.awslambda.scaladsl.AwsLambdaFlow
import akka.stream.scaladsl.{Sink, Source}
import by.matusievic.imagestorage.common.Implicits._
import by.matusievic.imagestorage.lamda.LambdaConfig._
import by.matusievic.imagestorage.lamda.wrapper.InvokeReq

import scala.concurrent.Future

trait LambdaService {
  def invoke(): Future[Done]
}

class LambdaServiceImpl extends LambdaService {
  override def invoke(): Future[Done] = {
    Source
      .single(InvokeReq(FunctionName))
      .via(AwsLambdaFlow(1))
      .runWith(Sink.ignore)
  }
}

object LambdaService {
  private val instance = new LambdaServiceImpl()
  def apply(): LambdaService = instance
}
