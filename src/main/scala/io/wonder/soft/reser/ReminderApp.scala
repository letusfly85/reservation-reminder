package io.wonder.soft.reser

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.actor.{ActorSystem, Props}
import akka.event.{Logging, LoggingAdapter}
import akka.stream.{ActorMaterializer, Materializer}
import com.typesafe.config.{Config, ConfigFactory}
import io.wonder.soft.reser.application.routes.ReserveRoute
import io.wonder.soft.reser.domain.job.{SimpleJobExecutor, SimpleJobGenerator}
import io.wonder.soft.reser.infra.{DockerManageActor, DockerService, ProgressHandleActor}

import scala.concurrent.ExecutionContextExecutor

trait ReminderApp extends AppModule {
  implicit val system: ActorSystem
  implicit val executor: ExecutionContextExecutor
  implicit val materializer: Materializer
  val startTimeMillis = System.currentTimeMillis()

  def config: Config
  def logger: LoggingAdapter

  def reserveRoute = new ReserveRoute(reserveService)

  val routes =
    path("api" / "v1" / "status") {
      (get | post) {
        val actor = system.actorOf(Props[DockerManageActor])
        actor ! ('pull, "nginx")

        complete("alive")
      }
    } ~ pathPrefix("api" / "v1") {
      reserveRoute.route
    }

}

//FIXME remove warning of 'ReminderApp has a main method with parameter type Array[String], but...'
object ReminderApp extends App with ReminderApp {
  override implicit val system: ActorSystem = ActorSystem("reservation-reminder")
  override implicit val executor: ExecutionContextExecutor = system.dispatcher
  override implicit val materializer: Materializer = ActorMaterializer()

  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass)

  Http().bindAndHandle(routes, "0.0.0.0", 8081)
}

