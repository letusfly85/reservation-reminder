package io.wonder.soft.reser.domain.entity

import io.getquill._
import io.wonder.soft.reser.infra.DBConfig

import scala.concurrent.Await
import scala.concurrent.duration._

case class Reserves (id: Int, name: String)

object Reserves extends DBConfig {
  import scala.concurrent.ExecutionContext.Implicits.global
  val reserves = quote {
    querySchema[Reserves]("reserves")
  }

  def find(id: Int) = {
    val result = this.run(quote {
      reserves.filter(u => u.id == lift(id))
    })

    Await.result(result, 5.seconds)
  }
}

