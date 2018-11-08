package io.wonder.soft.reser.application.services

import io.wonder.soft.reser.domain.entity.JobTransactionEntity
import io.wonder.soft.reser.domain.repository.JobTransactionRepository

import scala.concurrent.Future

class JobTransactionService(jobTransactionRepository: JobTransactionRepository) {

  def searchByStatus(statusName: String): Future[List[JobTransactionEntity]] = jobTransactionRepository.findByStatus(statusName)

}
