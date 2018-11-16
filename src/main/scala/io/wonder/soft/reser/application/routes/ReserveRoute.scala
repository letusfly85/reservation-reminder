package io.wonder.soft.reser.application.routes


import akka.http.scaladsl.server.Directives._
import io.wonder.soft.reser.application.services.ReserveService
import io.circe.generic.auto._
import io.circe.syntax._
import akka.http.scaladsl.model.StatusCodes
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.wonder.soft.reser.domain.entity.{ErrorResponseEntity, ReserveEntity}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

class ReserveRoute(reserveService: ReserveService)(implicit executionContext: ExecutionContext) extends FailFastCirceSupport {
  val prefix = "reserves"

  val route = pathPrefix(prefix) {
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
        Try {
          for {
            entity <- reserveService.findT(id.toInt).value
            json <- Future(entity.get)
          } yield json

        } match {
          case Success(json) =>
            completeOrRecoverWith(json) { extraction =>
              failWith(extraction)
            }
          case Failure(ex) => complete("ok")
        }
      }

    } ~ put {
      entity(as[ReserveEntity]) { reserveEntity =>
        val newValue = reserveService.update(reserveEntity).value

        val reserveJson = newValue.map { reserveEntities =>
          reserveEntities match {
            case Right(entity) => {
              StatusCodes.OK -> entity.asJson
            }

            case Left(ex) => {
              StatusCodes.NotFound ->
                ErrorResponseEntity.NotFoundEntity(
                  path = prefix, method = "PUT",
                  message = ex.getMessage, targetResource = ""
                ).asJson
            }
          }
        }
        completeOrRecoverWith(reserveJson) { extraction =>
          failWith(extraction)
        }
      }
    } ~ post {
      entity(as[ReserveEntity]) { reserveEntity =>
        val newValue = reserveService.create(reserveEntity).value

        val reserveJson = newValue.map { reserveEntities =>
          reserveEntities match {
            case Right(entity) => {
              StatusCodes.OK -> entity.asJson
            }

            case Left(ex) => {
              StatusCodes.NotFound ->
                ErrorResponseEntity.NotFoundEntity(
                  path = prefix, method = "POST",
                  message = ex.getMessage, targetResource = ""
                ).asJson
            }
          }
        }
        completeOrRecoverWith(reserveJson) { extraction =>
          failWith(extraction)
        }
      }
    }
  }
}
