package io.wonder.soft.reser.application.services

import io.wonder.soft.reser.domain.entity.ReserveEntity
import io.wonder.soft.reser.domain.repository.ReserveRepository

class ReserveService(reserveRepository: ReserveRepository) {

  def find(id: Int) = reserveRepository.find(id)

  def searchByUserId(userId: String) = reserveRepository.searchByUserId(userId)

  def updateT(reserveEntity: ReserveEntity)= reserveRepository.updateT(reserveEntity)

}
