package io.wonder.soft.reser.domain.entity

import io.circe.Decoder.Result
import io.circe.{Decoder, Encoder, HCursor, Json}
import org.joda.time.DateTime

final case class ReserveEntity
(
  id: Int,
  reservedUserId: String,
  name: String,
  description: Option[String] = None,
  command: Option[String] = None,
  reservedFrom: DateTime,
  reservedTo: DateTime,
  allDayFlag: Boolean = false,
  cronCycle: Option[String] = None,
  executedAt: Option[DateTime] = None,
  canceledAt: Option[DateTime] = None
) {
  require(reservedUserId.nonEmpty , "reservedUserId.empty")
  require(name.nonEmpty, "name.empty")
}

object ReserveEntity {
  // https://stackoverflow.com/questions/41431085/how-to-encode-decode-timestamp-for-json-in-circe
  implicit val TimestampFormat : Encoder[DateTime] with Decoder[DateTime] = new Encoder[DateTime] with Decoder[DateTime] {
    override def apply(a: DateTime): Json =
      Json.fromString(a.toString("yyyy-MM-dd'T'HH:mm:ss"))

    override def apply(c: HCursor): Result[DateTime] = Decoder.decodeString.map(s => new DateTime(s)).apply(c)
  }

  implicit def encodeReserve: Encoder[ReserveEntity] = Encoder.forProduct11(
    "id",
    "reserved_user_id",
    "name",
    "description",
    "command",
    "reserved_from",
    "reserved_to",
    "all_day_flag",
    "cron_cycle",
    "executed_at",
    "canceled_at"
  )(reserveEntity => (
    reserveEntity.id,
    reserveEntity.reservedUserId,
    reserveEntity.name,
    reserveEntity.description,
    reserveEntity.command,
    reserveEntity.reservedFrom,
    reserveEntity.reservedTo,
    reserveEntity.allDayFlag,
    reserveEntity.cronCycle,
    reserveEntity.executedAt,
    reserveEntity.canceledAt
  ))

  implicit val decodeReserve: Decoder[ReserveEntity] = Decoder.forProduct11(
    "id",
    "reserved_user_id",
    "name",
    "description",
    "command",
    "reserved_from",
    "reserved_to",
    "all_day_flag",
    "cron_cycle",
    "executed_at",
    "canceled_at"
  )((id: Int,
     reservedUserId: String,
     name: String,
     description: Option[String],
     command: Option[String],
     reservedFrom: DateTime,
     reservedTo: DateTime,
     allDayFlag: Boolean,
     cronCycle: Option[String],
     executedAt: Option[DateTime],
     canceledAt: Option[DateTime]) => {
    new ReserveEntity(
      id = id,
      reservedUserId = reservedUserId.toString,
      name = name.toString,
      description = description,
      command = command,
      reservedFrom = reservedFrom,
      reservedTo = reservedTo,
      allDayFlag = allDayFlag,
      cronCycle = cronCycle,
      executedAt = executedAt,
      canceledAt = canceledAt
    )
  })
}
