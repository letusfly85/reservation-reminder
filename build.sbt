organization := "io.wonder.soft"

version := "1.0.0-SNAPSHOT"

name := "reserve-reminder"

scalaVersion := "2.12.8"

lazy val `reser` = (project in file(".")).enablePlugins(DockerPlugin)

libraryDependencies ++= {
  val akkaV       = "2.5.19"
  val akkaHttpV   = "10.1.7"
  val quillV = "2.6.0"
  val circeV = "0.9.3"
  val macwireV = "2.3.1"
  Seq(
    "com.typesafe.akka" %% "akka-actor"              % akkaV,
    "com.typesafe.akka" %% "akka-persistence"        % akkaV,
    "com.typesafe.akka" %% "akka-persistence-query"  % akkaV,
    "com.typesafe.akka" %% "akka-slf4j"              % akkaV,
    "ch.qos.logback"    %  "logback-classic"         % "1.1.7",
    "com.typesafe.akka" %% "akka-stream"             % akkaV,
    "com.typesafe.akka" %% "akka-http"               % akkaHttpV,
    "ch.megard" %% "akka-http-cors" % "0.3.3",
    "com.softwaremill.akka-http-session" %% "core" % "0.5.6",
    "com.github.dnvriend" %% "akka-persistence-jdbc" % "3.4.0",
    "mysql" % "mysql-connector-java" % "5.1.47",
    "io.getquill" %% "quill-core" % quillV,
    "io.getquill" %% "quill-jdbc" % quillV,
    "io.getquill" %% "quill-async-mysql" % quillV,
    "com.zaxxer" % "HikariCP" % "3.2.0",
    "io.circe" %% "circe-core" % circeV,
    "io.circe" %% "circe-generic" % circeV,
    "io.circe" %% "circe-parser" % circeV,
    "de.heikoseeberger" %% "akka-http-circe" % "1.21.0",
    "com.softwaremill.macwire" %% "macros" % macwireV,
    "org.typelevel" %% "cats-core" % "1.2.0",
    "org.quartz-scheduler" % "quartz" % "2.3.0",
    "com.spotify" % "docker-client" % "8.14.4",
    "com.nulab-inc" %% "scala-oauth2-core" % "1.3.0"
  )
}

//refs: https://github.com/gerferra/amphip/blob/master/build.sbt
scalacOptions ++= Seq(
  "-Ypatmat-exhaust-depth", "off",
  "-Ypartial-unification"
)
