package io.wonder.soft.reser.application.routes


import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.{Encoder, Json}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.stream.Materializer
import org.joda.time.DateTime

import scala.concurrent.ExecutionContext
import io.wonder.soft.reser.application.services.AuthService

class AuthRoute(authService: AuthService)
               (implicit executionContext: ExecutionContext, system: ActorSystem, materializer: Materializer)
  extends FailFastCirceSupport {

  implicit val localDateEncoder = new Encoder[DateTime] {
    final def apply(a: DateTime): Json =
      Json.fromString(a.toString("yyyy-MM-dd'T'HH:mm:ss"))
  }

  val prefix = "auth"

  val route = path(prefix) {
    get {
      complete(StatusCodes.OK)
    }
  }

}
