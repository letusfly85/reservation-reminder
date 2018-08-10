package io.wonder.soft.reser.application.routes


import akka.http.scaladsl.server.Directives._
import io.wonder.soft.reser.application.services.ReserveService
import io.circe.generic.auto._
import io.circe.syntax._
import akka.http.scaladsl.model.StatusCodes
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._

class ReserveRoute(reserveService: ReserveService)(implicit executionContext: ExecutionContext) extends FailFastCirceSupport {

  val route = pathPrefix("reserves") {
    get {
      parameters('userId) { userId =>
        val futureReserves = reserveService.searchByUserId(userId)
        val reserveJson = futureReserves.map { reserveEntities =>
          reserveEntities.map(_.asJson).asJson
        }

        completeOrRecoverWith(reserveJson) { extraction =>
          failWith(extraction)
        }
      }
    } ~ pathPrefix("reserves" / Segment ) { id =>
      get {
        reserveService.find(id.toInt) match {
          case Some(reserve) => complete(reserve.asJson)
          case None => complete("")
        }
      }
    }
    /*~
      pathPrefix(Segment) { id =>
        pathEndOrSingleSlash {
          get {
            complete(getProfile(id).map {
              case Some(profile) =>
                OK -> profile.asJson
              case None =>
                BadRequest -> None.asJson
            })
          } ~
            post {
              entity(as[UserProfileUpdate]) { userUpdate =>
                complete(updateProfile(id, userUpdate).map {
                  case Some(profile) =>
                    OK -> profile.asJson
                  case None =>
                    BadRequest -> None.asJson
                })
              }
            }
        }
      }
      */
  }

}
