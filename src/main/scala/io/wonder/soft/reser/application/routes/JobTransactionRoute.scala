package io.wonder.soft.reser.application.routes


import akka.http.scaladsl.server.Directives._
import io.wonder.soft.reser.application.services.JobTransactionService
import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import org.joda.time.DateTime

import scala.concurrent.ExecutionContext

class JobTransactionRoute(jobTransactionService: JobTransactionService)(implicit executionContext: ExecutionContext) extends FailFastCirceSupport {
  implicit val localDateEncoder = new Encoder[DateTime] {
    final def apply(a: DateTime): Json =
      Json.fromString(a.toString("yyyy-MM-dd"))
  }

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
