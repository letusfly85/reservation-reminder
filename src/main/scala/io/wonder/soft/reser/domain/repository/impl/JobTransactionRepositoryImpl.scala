package io.wonder.soft.reser.domain.repository.impl

import cats.data._
import cats.implicits._

import scala.concurrent.Future
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
    * @param transactionEntity
    * @return
    */
  def create(transactionEntity: JobTransactionEntity): EitherT[Future, Throwable, JobTransactionEntity] = {
    Try {
      this.run(quote {
        query[JobTransactionEntity].insert(lift(transactionEntity))
      })
    } match {
      case Success(_) =>
        EitherT.right[Throwable](Future {transactionEntity})

      case Failure(exception) =>
        EitherT.left[JobTransactionEntity](Future.successful(exception))
    }
  }

  /**
    * TODO
    * @param transactionEntity
    * @return
    */
  def update(transactionEntity: JobTransactionEntity): EitherT[Future, Throwable, JobTransactionEntity] = {
    Try {
      this.run(quote {
        transactions.filter(u => u.id == lift(transactionEntity.id)).update(lift(transactionEntity))
      })
    } match {
      case Success(_) =>
        EitherT.right[Throwable](Future {transactionEntity})

      case Failure(exception) =>
        EitherT.left[JobTransactionEntity](Future.successful(exception))
    }
  }

}
