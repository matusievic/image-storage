
val ScalatraVersion = "2.7.1"

ThisBuild / scalaVersion := "2.13.4"
ThisBuild / organization := "by.matusievic"

lazy val hello = (project in file("."))
  .settings(
    name := "Image Storage",
    version := "0.1.0",
    libraryDependencies ++= Seq(
      "org.scalatra" %% "scalatra" % ScalatraVersion,
      "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
      "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
      "org.eclipse.jetty" % "jetty-webapp" % "9.2.15.v20160210" % "container;compile",
      "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
      "com.github.seratch" %% "awscala-s3" % "0.8.+",
      "org.tpolecat" %% "doobie-core"      % "0.9.0",
      "org.tpolecat" %% "doobie-postgres"  % "0.9.0"
    ),
  )

enablePlugins(SbtTwirl)
enablePlugins(JettyPlugin)


assemblyOutputPath in assembly := new File("app/image-storage.jar")
mainClass in assembly := Some("App")
assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
