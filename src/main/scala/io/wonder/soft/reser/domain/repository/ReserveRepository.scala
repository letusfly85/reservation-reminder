package io.wonder.soft.reser.domain.repository

import cats.data.{EitherT, OptionT}
import io.wonder.soft.reser.domain.entity.ReserveEntity

import scala.concurrent.Future

trait ReserveRepository {

  def findT(id: Int): OptionT[Future, ReserveEntity]

  def find(id: Int): Option[ReserveEntity]

  def searchByUserId(userId: String): Future[List[ReserveEntity]]

  def updateT(reserveEntity: ReserveEntity): EitherT[Future, Throwable, ReserveEntity]

  def destroyT(id: Int): EitherT[Future, Throwable, ReserveEntity]

}
