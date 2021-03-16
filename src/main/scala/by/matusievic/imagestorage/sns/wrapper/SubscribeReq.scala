package by.matusievic.imagestorage.sns.wrapper

import software.amazon.awssdk.services.sns.model.SubscribeRequest

object SubscribeReq {
  def apply(topicArn: String, email: String): SubscribeRequest =
    SubscribeRequest.builder().topicArn(topicArn).protocol("email").endpoint(email).build()
}
