package io.wonder.soft.reser

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.ContentTypes._
import akka.stream.{ActorMaterializer, Materializer}
import akka.util.ByteString
import com.typesafe.config.{Config, ConfigFactory}
import io.wonder.soft.reser.application.routes.ReserveRoute
import io.wonder.soft.reser.application.services.ReserveService
import io.wonder.soft.reser.domain.repository.impl.ReserveRepositoryImpl

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
        complete("alive")
      }
    } ~ pathPrefix("api" / "v1") {
      reserveRoute.route
    }

}

object ReminderApp extends App with ReminderApp {
  override implicit val system: ActorSystem = ActorSystem("reservation-reminder")
  override implicit val executor: ExecutionContextExecutor = system.dispatcher
  override implicit val materializer: Materializer = ActorMaterializer()

  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass)

  Http().bindAndHandle(routes, "0.0.0.0", 8080)
}

