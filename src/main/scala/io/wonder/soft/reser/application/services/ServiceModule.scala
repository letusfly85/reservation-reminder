package io.wonder.soft.reser.application.services

import io.wonder.soft.reser.domain.repository.impl.ReserveRepositoryImpl

trait ServiceModule {
  import com.softwaremill.macwire._

  lazy val reserveRepository = wire[ReserveRepositoryImpl]

}
