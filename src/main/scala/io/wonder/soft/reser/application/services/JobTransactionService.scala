package io.wonder.soft.reser.application.services

import io.wonder.soft.reser.domain.entity.JobTransactionEntity
import io.wonder.soft.reser.domain.repository.JobTransactionRepository

import scala.concurrent.{ExecutionContext, Future}

class JobTransactionService(jobTransactionRepository: JobTransactionRepository)(implicit executionContext: ExecutionContext) {

  def searchByStatus(statusName: String): Future[List[JobTransactionEntity]] = jobTransactionRepository.findByStatus(statusName)

  def create(entity: JobTransactionEntity) = jobTransactionRepository.create(entity)

  def runScheduleActors = {
    val transactions = this.searchByStatus("PENDING")
    transactions.map(ts => ts.foreach { transaction =>
      //TODO run actor
      transaction
    })
  }

}
