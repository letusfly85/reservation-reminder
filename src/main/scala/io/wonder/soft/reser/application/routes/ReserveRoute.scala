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

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}
import io.wonder.soft.reser.application.services.ReserveService
import io.wonder.soft.reser.domain.entity.{ErrorResponseEntity, ReserveEntity}

class ReserveRoute(reserveService: ReserveService)
                  (implicit executionContext: ExecutionContext, system: ActorSystem, materializer: Materializer)
  extends FailFastCirceSupport {
  import ReserveEntity._

  implicit val localDateEncoder = new Encoder[DateTime] {
    final def apply(a: DateTime): Json =
      Json.fromString(a.toString("yyyy-MM-dd'T'HH:mm:ss"))
  }

  val prefix = "reserves"

  val route =
    path(prefix) {
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
                StatusCodes.InternalServerError ->
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
          /*
          val command = reserveEntity.command.getOrElse(s"echo ${reserveEntity.name}")
          val job = SimpleJobGenerator.generateJob(reserveEntity.name, "test", command)
          val trigger = SimpleJobGenerator.generateTrigger(reserveEntity.name, "test", new Date(), 1, 0)

          SimpleJobExecutor.startSchedule(job, trigger)
          */

          val reserveJson = newValue.map { reserveEntities =>
            reserveEntities match {
              case Right(entity) => {
                StatusCodes.OK -> entity.asJson
              }

              case Left(ex) => {
                StatusCodes.InternalServerError ->
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
