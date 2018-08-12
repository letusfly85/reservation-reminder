package io.wonder.soft.reser.application.routes


import akka.http.scaladsl.server.Directives._
import io.wonder.soft.reser.application.services.ReserveService
import io.circe.generic.auto._
import io.circe.syntax._
import akka.http.scaladsl.model.StatusCodes
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.wonder.soft.reser.domain.entity.NotFoundExceptionEntity

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class ReserveRoute(reserveService: ReserveService)(implicit executionContext: ExecutionContext) extends FailFastCirceSupport {

  val route = pathPrefix("reserves") {
    parameters('userId) { userId =>
      get {
        val futureReserves = reserveService.searchByUserId(userId)
        val reserveJson = futureReserves.map { reserveEntities =>
          reserveEntities.map(_.asJson).asJson
        }

        completeOrRecoverWith(reserveJson) { extraction =>
          failWith(extraction)
        }
      }
    } ~ path(Segment) { id =>
      get {
        reserveService.find(id.toInt) match {
          case Some(reserve) =>
            complete(StatusCodes.OK, reserve.asJson)

          case None =>
            complete(
              StatusCodes.NotFound,
              NotFoundExceptionEntity(message = s"${id} not found for reserves").asJson
            )
        }
      } ~ post {
        //TODO implement
        reserveService.find(id.toInt) match {
          case Some(reserve) =>
            complete(StatusCodes.OK, reserve.asJson)

          case None =>
            complete(
              StatusCodes.NotFound,
              NotFoundExceptionEntity(message = s"${id} not found for reserves").asJson
            )
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
