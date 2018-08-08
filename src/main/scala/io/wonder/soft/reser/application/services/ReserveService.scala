package io.wonder.soft.reser.application.services

import io.wonder.soft.reser.domain.repository.ReserveRepository

class ReserveService(reserveRepository: ReserveRepository) {

  def searchByUserId(userId: String) = reserveRepository.searchByUserId(userId)

}
