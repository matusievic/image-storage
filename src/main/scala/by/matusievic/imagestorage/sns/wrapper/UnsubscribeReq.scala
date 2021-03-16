package by.matusievic.imagestorage.sns.wrapper

import software.amazon.awssdk.services.sns.model.UnsubscribeRequest

object UnsubscribeReq {
  def apply(subscriptionArn: String): UnsubscribeRequest =
    UnsubscribeRequest.builder().subscriptionArn(subscriptionArn).build()
}
