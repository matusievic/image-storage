package by.matusievic.imagestorage.sns

import by.matusievic.imagestorage.common.AkkaConfig._
import com.github.matsluni.akkahttpspi.AkkaHttpClient
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsAsyncClient

object SnsConfig {
  implicit val awsSnsClient: SnsAsyncClient =
    SnsAsyncClient
      .builder()
      .credentialsProvider(DefaultCredentialsProvider.create())
      .region(Region.EU_CENTRAL_1)
      .httpClient(AkkaHttpClient.builder().withActorSystem(system).build())
      .build()

  val topicArn: String = sys.env.getOrElse("TOPIC_ARN", "UNKNOWN")

  system.registerOnTermination(awsSnsClient.close())
}
