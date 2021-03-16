package by.matusievic.imagestorage.sns

import akka.Done
import akka.stream.alpakka.sns.scaladsl.SnsPublisher
import akka.stream.scaladsl.Source
import by.matusievic.imagestorage.common.Implicits._
import by.matusievic.imagestorage.sns.SnsConfig._
import by.matusievic.imagestorage.sns.wrapper.{ListSubscriptionsReq, PublishReq, SubscribeReq, UnsubscribeReq}

import scala.concurrent.Future
import scala.jdk.CollectionConverters._
import scala.jdk.FutureConverters._

trait SnsService {
  def publish(message: String): Future[Done]

  def subscribe(email: String): Future[Any]

  def unsubscribe(email: String): Future[Unit]
}

class SmsServiceImpl extends SnsService {
  override def publish(message: String): Future[Done] = {
    Source
      .single(PublishReq(topicArn, message))
      .runWith(SnsPublisher.publishSink())
  }

  override def subscribe(email: String): Future[Any] = {
    awsSnsClient.subscribe(SubscribeReq(topicArn, email)).asScala
  }

  override def unsubscribe(email: String): Future[Unit] = {
    awsSnsClient.listSubscriptionsByTopic(ListSubscriptionsReq(topicArn)).asScala.map {
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