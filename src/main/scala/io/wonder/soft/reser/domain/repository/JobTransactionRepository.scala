package io.wonder.soft.reser.domain.repository

import io.wonder.soft.reser.domain.entity.JobTransactionEntity

import scala.concurrent.Future

trait JobTransactionRepository {

  def findByStatus(statusName: String): Future[List[JobTransactionEntity]]

}
