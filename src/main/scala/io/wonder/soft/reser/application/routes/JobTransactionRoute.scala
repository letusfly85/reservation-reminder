package io.wonder.soft.reser.application.routes

import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import io.circe.syntax._
import io.wonder.soft.reser.application.services.JobTransactionService

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

class JobTransactionRoute(jobTransactionService: JobTransactionService)(implicit executionContext: ExecutionContext) extends FailFastCirceSupport {

  val route = pathPrefix("job-transactions") {
    parameters('statusName) { statusName =>
      get {
        val futureJobTransactions = jobTransactionService.searchByStatus(statusName)
        val jobTransactionJson = futureJobTransactions.map { jobTransactionEntities =>
          jobTransactionEntities.map(_.asJson).asJson
        }

        completeOrRecoverWith(jobTransactionJson) { extraction =>
          failWith(extraction)
        }
      }
    }
  }
}
