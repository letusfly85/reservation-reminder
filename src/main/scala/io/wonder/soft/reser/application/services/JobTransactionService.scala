package io.wonder.soft.reser.application.services

import java.util.Date

import akka.actor.{ActorSystem, Props}
import akka.stream.{ActorMaterializer, Materializer}
import io.wonder.soft.reser.domain.entity.JobTransactionEntity
import io.wonder.soft.reser.domain.job.{SimpleJobExecutor, SimpleJobGenerator}
import io.wonder.soft.reser.domain.repository.{JobTransactionRepository, ReserveRepository}

import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}
import scala.concurrent.duration._

class JobTransactionService
 (jobTransactionRepository: JobTransactionRepository,
  reserveRepository: ReserveRepository)
 (implicit executionContext: ExecutionContext) {

  implicit val system: ActorSystem = ActorSystem("job-transaction")
  implicit val executor: ExecutionContextExecutor = system.dispatcher
  implicit val materializer: Materializer = ActorMaterializer()

  def searchByStatus(statusName: String): Future[List[JobTransactionEntity]] = jobTransactionRepository.findByStatus(statusName)

  def create(entity: JobTransactionEntity) = jobTransactionRepository.create(entity)

  def runPendingJobs = {
    val futureTransactions = this.searchByStatus("PENDING")

    val transactions = Await.result(futureTransactions, 5.seconds)
    transactions.foreach {transaction =>
      val reservedId = transaction.reserveId
      reserveRepository.find(reservedId) match {
        case Some(reserveEntity) =>
          // val command = reserveEntity.command
          val command = s"echo ${reserveEntity.description}"
          val job = SimpleJobGenerator.generateJob(reserveEntity.name, "test", command)
          val trigger = SimpleJobGenerator.generateTrigger(reserveEntity.name, "test", new Date(), 1, 0)

          SimpleJobExecutor.startSchedule(job, trigger)
      }

    }
  }

}
