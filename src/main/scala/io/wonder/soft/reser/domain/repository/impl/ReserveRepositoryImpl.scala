package io.wonder.soft.reser.domain.repository.impl


import cats.data._
import cats.implicits._
import io.wonder.soft.reser.infra.DBConfig
import io.wonder.soft.reser.domain.entity.ReserveEntity
import io.wonder.soft.reser.domain.repository.ReserveRepository

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import io.getquill._

import scala.util.{Failure, Success, Try}


class ReserveRepositoryImpl extends DBConfig with ReserveRepository {
  import scala.concurrent.ExecutionContext.Implicits.global

  val reserves = quote {
    querySchema[ReserveEntity]("reserves",
      _.reservedUserId -> "reserved_user_id",
      _.executedAt -> "executed_at",
      _.name -> "name",
      _.description -> "description",
      _.command  -> "command",
      _.allDayFlag -> "all_day_flag",
      _.cronCycle -> "cron_cycle",
      _.reservedFrom -> "reserved_from",
      _.reservedTo -> "reserved_to",
      _.canceledAt -> "canceled_at"
    )
  }

  def findT(id: Int): OptionT[Future, ReserveEntity] = {
    val query = this.run(quote {
      reserves.filter(u => u.id == lift(id))
    })

    OptionT(query.map(future => future.headOption))
  }

  def find(id: Int) = {
    val result = this.run(quote {
      reserves.filter(u => u.id == lift(id))
    })

    Try {
      Await.result(result, 5.seconds)
    } match {
      case Success(result) => result.headOption
      case Failure(_) => None
    }
  }

  def searchByUserId(userId: String): Future[List[ReserveEntity]] = {
    this.run(quote {
      reserves.filter(u => u.reservedUserId == lift(userId))
    })
  }

  def create(reserveEntity: ReserveEntity): EitherT[Future, Throwable, ReserveEntity] = {
    Try {
      val newEntity = reserveEntity
      this.run(quote {
        reserves.insert(
          _.reservedUserId -> lift(newEntity.reservedUserId),
          _.executedAt -> lift(newEntity.executedAt),
          _.name -> lift(newEntity.name),
          _.description -> lift(newEntity.description),
          _.command -> lift(newEntity.command),
          _.reservedFrom -> lift(newEntity.reservedFrom),
          _.reservedTo -> lift(newEntity.reservedTo),
          _.canceledAt -> lift(newEntity.canceledAt),
          _.allDayFlag -> lift(newEntity.allDayFlag)
        )
      })
    } match {
      case Success(_) =>
        EitherT.right[Throwable](Future {reserveEntity})

      case Failure(exception) =>
        EitherT.left[ReserveEntity](Future.successful(exception))
    }
  }


  def update(reserveEntity: ReserveEntity): EitherT[Future, Throwable, ReserveEntity] = {
    Try {
      this.run(quote {
        reserves.filter(u => u.id == lift(reserveEntity.id)).update(lift(reserveEntity))
      })
    } match {
      case Success(_) =>
        EitherT.right[Throwable](Future {reserveEntity})

      case Failure(exception) =>
        EitherT.left[ReserveEntity](Future.successful(exception))
    }
  }

  def destroyT(id: Int): EitherT[Future, Throwable, ReserveEntity] = {
    Try {
      val entity = this.find(id).get
      this.run(quote {
        reserves.filter(u => u.id == lift(entity.id)).delete
      })
      entity
    } match {
      case Success(entity) => EitherT.right[Throwable](Future.successful(entity))
      case Failure(exception) => EitherT.left[ReserveEntity](Future.successful(exception))
      }
  }

}
