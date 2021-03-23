package by.matusievic.imagestorage.lamda

import by.matusievic.imagestorage.common.Implicits._
import com.github.matsluni.akkahttpspi.AkkaHttpClient
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.services.lambda.LambdaAsyncClient

object LambdaConfig {
  val FunctionName = "create_bucket"

  implicit val lambdaClient: LambdaAsyncClient = LambdaAsyncClient
    .builder()
    .credentialsProvider(DefaultCredentialsProvider.create())
    .httpClient(AkkaHttpClient.builder().withActorSystem(system).build())
    .build()
}
