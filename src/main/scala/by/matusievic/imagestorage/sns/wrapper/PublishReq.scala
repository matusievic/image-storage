package by.matusievic.imagestorage.sns.wrapper

import software.amazon.awssdk.services.sns.model.PublishRequest

object PublishReq {
  def apply(topicArn: String, message: String): PublishRequest =
    PublishRequest.builder().topicArn(topicArn).message(message).build()
}
