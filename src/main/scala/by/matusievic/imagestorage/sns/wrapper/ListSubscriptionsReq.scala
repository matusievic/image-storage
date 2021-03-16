package by.matusievic.imagestorage.sns.wrapper

import software.amazon.awssdk.services.sns.model.ListSubscriptionsByTopicRequest

object ListSubscriptionsReq {
  def apply(topicArn: String): ListSubscriptionsByTopicRequest =
    ListSubscriptionsByTopicRequest.builder().topicArn(topicArn).build()
}
