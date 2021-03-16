package by.matusievic.imagestorage.sns

import akka.stream.alpakka.sns.scaladsl.SnsPublisher
import akka.stream.scaladsl.Source
import by.matusievic.imagestorage.sns.SnsConfig._
import by.matusievic.imagestorage.sns.wrapper.{PublishReq, SubscribeReq, UnsubscribeReq}

import scala.jdk.CollectionConverters._
import scala.jdk.FutureConverters._

trait SnsService {
  def publish(message: String)

  def subscribe(email: String)

  def unsubscribe(email: String)
}

class SmsServiceImpl extends SnsService {
  override def publish(message: String): Unit = {
    Source
      .single(PublishReq(topicArn, message))
      .runWith(SnsPublisher.publishSink()).foreach(_ => ())
  }

  override def subscribe(email: String): Unit = {
    awsSnsClient.subscribe(SubscribeReq(topicArn, email))
  }

  override def unsubscribe(email: String): Unit = {
    awsSnsClient.listSubscriptions().asScala.map {
      _.subscriptions()
        .asScala
        .find(_.endpoint() == email)
        .foreach(s => awsSnsClient.unsubscribe(UnsubscribeReq(s.subscriptionArn())))
    }
  }
}

object SnsService {
  private val instance = new SmsServiceImpl()
  def apply(): SnsService = instance
}