
val scalatraVersion = "2.7.1"
val alpakkaVersion = "2.0.2"
val akkaStreamVersion = "2.5.32"

ThisBuild / scalaVersion := "2.13.4"
ThisBuild / organization := "by.matusievic"

lazy val hello = (project in file("."))
  .settings(
    name := "Image Storage",
    version := "0.1.0",
    libraryDependencies ++= Seq(
      "org.scalatra" %% "scalatra" % scalatraVersion,
      "org.scalatra" %% "scalatra-scalatest" % scalatraVersion % "test",
      "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
      "org.eclipse.jetty" % "jetty-webapp" % "9.2.15.v20160210" % "container;compile",
      "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
      "org.tpolecat" %% "doobie-core" % "0.9.0",
      "org.tpolecat" %% "doobie-postgres" % "0.9.0",
      "com.lightbend.akka" %% "akka-stream-alpakka-s3" % alpakkaVersion,
      "com.lightbend.akka" %% "akka-stream-alpakka-sns" % alpakkaVersion,
      "com.lightbend.akka" %% "akka-stream-alpakka-awslambda" % alpakkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaStreamVersion
    ),
  )

enablePlugins(SbtTwirl)
enablePlugins(JettyPlugin)


assemblyOutputPath in assembly := new File("app/image-storage.jar")
mainClass in assembly := Some("App")
assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case PathList("reference.conf") => MergeStrategy.concat
  case x => MergeStrategy.first
}
