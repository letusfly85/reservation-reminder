organization := "io.wonder-soft"

version := "1.0.0-SNAPSHOT"

name := "reservation-reminder"

scalaVersion := "2.12.4"

libraryDependencies ++= {
  val akkaV       = "2.5.8"
  Seq(
    "com.typesafe.akka" %% "akka-actor"              % akkaV,
    "com.typesafe.akka" %% "akka-persistence"        % akkaV,
    "com.typesafe.akka" %% "akka-slf4j"              % akkaV,
    "ch.qos.logback"    %  "logback-classic"         % "1.1.7",
    "com.typesafe.akka" %% "akka-stream"             % akkaV,
    "com.enragedginger" %% "akka-quartz-scheduler" % "1.6.1-akka-2.5.x",
    "com.github.nscala-time" %% "nscala-time" % "2.18.0"
  )
}

//refs: https://github.com/gerferra/amphip/blob/master/build.sbt
scalacOptions ++= Seq(
  "-Ypatmat-exhaust-depth", "off"
)