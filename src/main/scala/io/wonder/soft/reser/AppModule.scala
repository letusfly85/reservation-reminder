package io.wonder.soft.reser

import io.wonder.soft.reser.application.services.ReserveService
import io.wonder.soft.reser.domain.repository.ReserveRepository
import io.wonder.soft.reser.domain.repository.impl.ReserveRepositoryImpl

trait AppModule {
  import com.softwaremill.macwire._

  lazy val reserveRepository: ReserveRepository = wire[ReserveRepositoryImpl]
  lazy val reserveService   = wire[ReserveService]

}
