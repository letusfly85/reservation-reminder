package io.wonder.soft.reser.domain.repository.impl

import cats.data.{EitherT, OptionT}
import io.wonder.soft.reser.infra.DBConfig
import io.wonder.soft.reser.domain.entity.ReserveEntity
import io.wonder.soft.reser.domain.repository.ReserveRepository

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import io.getquill._

import scala.util.{Failure, Success, Try}


class ReserveRepositoryImpl extends DBConfig with ReserveRepository {
  import scala.concurrent.ExecutionContext.Implicits.global

  val reserves = quote {
    querySchema[ReserveEntity]("reserves", _.reservedUserId -> "reserved_user_id")
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

  def destroy(id: Int): Either[Exception, ReserveEntity] = {
    this.find(id).map{ entity =>
      val result = this.run(quote {
        reserves.filter(u => u.id == lift(id)).delete
      })

      Try {
        Await.result(result, 5.seconds)
        entity
      } match {
        case Success(entity) =>  return Right(entity)
        case Failure(exception) => return Left(new RuntimeException(exception))
      }

    }.toRight(new Exception(s"reserves ${id} not found"))
  }

}
