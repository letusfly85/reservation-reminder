package io.wonder.soft.reser.domain.repository

import cats.data.EitherT
import io.wonder.soft.reser.domain.entity.JobTransactionEntity

import scala.concurrent.Future

trait JobTransactionRepository {

  def findByStatus(statusName: String): Future[List[JobTransactionEntity]]

  def create(transactionEntity: JobTransactionEntity): EitherT[Future, Throwable, JobTransactionEntity]

  def update(transactionEntity: JobTransactionEntity): EitherT[Future, Throwable, JobTransactionEntity]

}
