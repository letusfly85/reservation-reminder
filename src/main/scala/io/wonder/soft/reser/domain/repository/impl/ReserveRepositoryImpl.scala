package io.wonder.soft.reser.domain.repository.impl

import io.wonder.soft.reser.infra.DBConfig
import io.wonder.soft.reser.domain.entity.ReserveEntity
import io.wonder.soft.reser.domain.repository.ReserveRepository

import scala.concurrent.Await
import scala.concurrent.duration._
import io.getquill._

import scala.util.{Failure, Success, Try}


class ReserveRepositoryImpl extends DBConfig with ReserveRepository {
  import scala.concurrent.ExecutionContext.Implicits.global

  val reserves = quote {
    querySchema[ReserveEntity]("reserves")
  }

  def find(id: Int) = {
    val result = this.run(quote {
      reserves.filter(u => u.id == lift(id))
    })

    Try {
      Await.result(result, 5.seconds)
    } match {
      case Success(result) => result.headOption
      case Failure(exception) => None
    }
  }

  def searchByUserId(userId: String): List[ReserveEntity] = {
    List.empty[ReserveEntity]
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
