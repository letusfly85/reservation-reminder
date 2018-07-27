package io.wonder.soft.reser.application.routes


import akka.http.scaladsl.server.Directives._
import io.wonder.soft.reser.application.services.ReserveService

import io.circe.generic.auto._
import io.circe.syntax._

import akka.http.scaladsl.model.StatusCodes
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import scala.concurrent.ExecutionContext

class ReserveRoute(reserveService: ReserveService)(implicit executionContext: ExecutionContext) extends FailFastCirceSupport {

  val route = pathPrefix("reserves") {
    pathEndOrSingleSlash {
      get {
        val reserves = reserveService.searchByUserId("")
        val json = reserves.map(_.asJson).asJson
        complete(json)
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