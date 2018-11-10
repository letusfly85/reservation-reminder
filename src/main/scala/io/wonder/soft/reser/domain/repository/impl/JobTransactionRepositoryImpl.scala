package io.wonder.soft.reser.domain.repository.impl

import cats.data._
import cats.implicits._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import io.getquill._

import scala.util.{Failure, Success, Try}

import io.wonder.soft.reser.infra.DBConfig

import io.wonder.soft.reser.domain.entity.JobTransactionEntity
import io.wonder.soft.reser.domain.repository.JobTransactionRepository

class JobTransactionRepositoryImpl extends DBConfig with JobTransactionRepository {
  import scala.concurrent.ExecutionContext.Implicits.global

  val transactions = quote {
    querySchema[JobTransactionEntity]("job_transactions", _.jobStatus -> "job_status")
  }

  def findByStatus(statusName: String): Future[List[JobTransactionEntity]] = {
    this.run(quote {
      transactions.filter(u => u.jobStatus == lift(statusName))
    })
  }

  /**
    * TODO
    * @param transactionEntity
    * @return
    */
  def create(transactionEntity: JobTransactionEntity): EitherT[Future, Throwable, JobTransactionEntity] = {
    ???
  }

}
