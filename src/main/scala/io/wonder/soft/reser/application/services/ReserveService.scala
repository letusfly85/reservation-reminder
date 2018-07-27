package io.wonder.soft.reser.application.services

import io.wonder.soft.reser.domain.entity.ReserveEntity
import io.wonder.soft.reser.domain.repository.ReserveRepository

class ReserveService () {
// class ReserveService (reserveRepository: ReserveRepository) {

  // def searchByUserId(userId: String) = reserveRepository.searchByUserId(userId)
  def searchByUserId(userId: String) = List(ReserveEntity(1, "test"))

}
