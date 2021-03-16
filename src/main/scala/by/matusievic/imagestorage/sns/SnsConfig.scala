package by.matusievic.imagestorage.sns

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import com.github.matsluni.akkahttpspi.AkkaHttpClient
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsAsyncClient

import scala.concurrent.ExecutionContext

object SnsConfig {
  implicit val system: ActorSystem = ActorSystem()
  implicit val mat: Materializer = ActorMaterializer()
  implicit val ec: ExecutionContext = ExecutionContext.global

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
