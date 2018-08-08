package io.wonder.soft.reser.domain.entity

final case class ReserveEntity (id: Int, reservedUserId: String, name: String) {
  require(id > 0, "larger.zero")
  require(reservedUserId.nonEmpty , "larger.zero")
  require(name.nonEmpty, "name.empty")
}

