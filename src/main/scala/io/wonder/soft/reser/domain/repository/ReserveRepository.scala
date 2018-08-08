package io.wonder.soft.reser.domain.repository

import cats.data.OptionT
import io.wonder.soft.reser.domain.entity.ReserveEntity

import scala.concurrent.Future

trait ReserveRepository {

  def findT(id: Int): OptionT[Future, ReserveEntity]

  def find(id: Int): Option[ReserveEntity]

  def searchByUserId(userId: String): Future[List[ReserveEntity]]

  def destroy(id: Int): Either[Exception, ReserveEntity]

}
