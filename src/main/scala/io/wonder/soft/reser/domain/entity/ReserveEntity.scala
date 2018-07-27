package io.wonder.soft.reser.domain.entity

final case class ReserveEntity (id: Int, name: String) {
  require(id > 0, "larger.zero")
  require(name.nonEmpty, "name.empty")
}

