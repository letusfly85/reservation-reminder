package io.wonder.soft.reser

import io.wonder.soft.reser.application.services.ReserveService

trait AppModule {
  import com.softwaremill.macwire._

  lazy val reserveService   = wire[ReserveService]

}
