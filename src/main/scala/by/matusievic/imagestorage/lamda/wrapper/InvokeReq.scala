package by.matusievic.imagestorage.lamda.wrapper

import software.amazon.awssdk.services.lambda.model.InvokeRequest

object InvokeReq {
  def apply(functionName: String): InvokeRequest =
    InvokeRequest.builder().functionName(functionName).build()
}
