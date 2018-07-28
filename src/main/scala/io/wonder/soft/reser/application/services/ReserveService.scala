package io.wonder.soft.reser.application.services

class ReserveService extends ServiceModule {

  def searchByUserId(userId: String) = reserveRepository.searchByUserId(userId)

}
