organization := "io.wonder.soft"

version := "1.0.0-SNAPSHOT"

name := "reservation-reminder"

scalaVersion := "2.12.6"

lazy val `reser` = (project in file(".")).enablePlugins(FlywayPlugin)

libraryDependencies ++= {
  val akkaV       = "2.5.13"
  val akkaHttpV   = "10.1.3"
  val quillV = "2.5.4"
  val circeV = "0.9.3"
  val macwireV = "2.3.1"
  Seq(
    "com.typesafe.akka" %% "akka-actor"              % akkaV,
    "com.typesafe.akka" %% "akka-persistence"        % akkaV,
    "com.typesafe.akka" %% "akka-slf4j"              % akkaV,
    "ch.qos.logback"    %  "logback-classic"         % "1.1.7",
    "com.typesafe.akka" %% "akka-stream"             % akkaV,
    "com.typesafe.akka" %% "akka-http"               % akkaHttpV,
    "com.enragedginger" %% "akka-quartz-scheduler" % "1.6.1-akka-2.5.x",
    "com.github.nscala-time" %% "nscala-time" % "2.18.0",
    "com.github.dnvriend" %% "akka-persistence-jdbc" % "3.4.0",
    "mysql" % "mysql-connector-java" % "5.1.46",
    "io.getquill" %% "quill-core" % quillV,
    "io.getquill" %% "quill-jdbc" % quillV,
    "io.getquill" %% "quill-async-mysql" % quillV,
    "com.zaxxer" % "HikariCP" % "3.2.0",
    "io.circe" %% "circe-core" % circeV,
    "io.circe" %% "circe-generic" % circeV,
    "io.circe" %% "circe-parser" % circeV,
    "de.heikoseeberger" %% "akka-http-circe" % "1.21.0",
    "com.softwaremill.macwire" %% "macros" % macwireV,
    "org.typelevel" %% "cats-core" % "1.2.0"
  )
}

//refs: https://github.com/gerferra/amphip/blob/master/build.sbt
scalacOptions ++= Seq(
  "-Ypatmat-exhaust-depth", "off",
  "-Ypartial-unification"
)

//********************************************************
// FlyWay settings
//********************************************************
import com.typesafe.config._

val conf = ConfigFactory.parseFile(new File("src/main/resources/database.flyway.conf")).resolve()

flywayDriver := conf.getString("db.default.driver")

flywayUrl := conf.getString("db.default.url")

flywayUser := conf.getString("db.default.username")

flywayPassword := conf.getString("db.default.password")

flywayLocations := Seq("filesystem:src/main/resources/db/migrations")

flywayTarget := conf.getString("migration.target.version")

flywayBaselineVersion := "0.0.0"
