package io.wonder.soft.reser.application.services

import io.wonder.soft.reser.domain.entity.ReserveEntity
import io.wonder.soft.reser.domain.repository.ReserveRepository

import scala.concurrent.Future

class ReserveService(reserveRepository: ReserveRepository) {

  def findT(id: Int) = reserveRepository.findT(id)

  def searchByUserId(userId: String): Future[List[ReserveEntity]] = reserveRepository.searchByUserId(userId)

  def updateT(reserveEntity: ReserveEntity)= reserveRepository.updateT(reserveEntity)

}
