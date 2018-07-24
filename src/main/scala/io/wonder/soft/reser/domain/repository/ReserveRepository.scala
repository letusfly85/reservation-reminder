package io.wonder.soft.reser.domain.repository

import io.wonder.soft.reser.domain.entity.ReserveEntity

trait ReserveRepository {

  def find(id: Int): Option[ReserveEntity]

}
