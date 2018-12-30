package io.wonder.soft.reser.application.routes


import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.{Encoder, Json}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.stream.Materializer
import com.softwaremill.session.{SessionConfig, SessionManager, SessionUtil}
import com.softwaremill.session.SessionDirectives._
import com.softwaremill.session.SessionOptions._
import com.softwaremill.session.SessionResult._
import com.softwaremill.session._
import org.joda.time.DateTime

import scala.concurrent.ExecutionContext
import io.wonder.soft.reser.application.services.AuthService
import io.wonder.soft.reser.domain.entity.{AuthSession, SignInEntity}


class AuthRoute(authService: AuthService)
               (implicit executionContext: ExecutionContext, system: ActorSystem, materializer: Materializer)
  extends FailFastCirceSupport {

  implicit val localDateEncoder = new Encoder[DateTime] {
    final def apply(a: DateTime): Json =
      Json.fromString(a.toString("yyyy-MM-dd'T'HH:mm:ss"))
  }

  val prefix = "auth"

  val sessionConfig = SessionConfig.default(SessionUtil.randomServerSecret())
  implicit val sessionManager = new SessionManager[AuthSession](sessionConfig)

  val route = pathPrefix(prefix) {
    get {
      complete(StatusCodes.OK)
    } ~ path("signIn") {
      post {
        entity(as[SignInEntity]) { signInEntity: SignInEntity =>
          if(signInEntity.userId == "TODO" && signInEntity.password == "TODO"){
            setSession(oneOff, usingCookies, AuthSession(signInEntity.userId)) {
              complete(StatusCodes.OK)
            }
          } else {
            complete(StatusCodes.Unauthorized)
          }
        }
      }
    }
  }

}
