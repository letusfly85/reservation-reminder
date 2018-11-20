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
  reservedFrom: DateTime,
  reservedTo: DateTime,
  jobId: Option[String] = None,
  jobStatus: Option[String] = Some("PENDING"),
  executedAt: Option[DateTime] = None,
  canceledAt: Option[DateTime] = None
) {
  require(id > 0, "larger.zero")
  require(reservedUserId.nonEmpty , "larger.zero")
  require(name.nonEmpty, "name.empty")
}

object ReserveEntity {
  // https://stackoverflow.com/questions/41431085/how-to-encode-decode-timestamp-for-json-in-circe
  implicit val TimestampFormat : Encoder[DateTime] with Decoder[DateTime] = new Encoder[DateTime] with Decoder[DateTime] {
    override def apply(a: DateTime): Json =
      Json.fromString(a.toString("yyyy-MM-dd'T'HH:mm:ss"))

    override def apply(c: HCursor): Result[DateTime] = Decoder.decodeString.map(s => new DateTime(s)).apply(c)
  }

  implicit def encodeReserve: Encoder[ReserveEntity] = Encoder.forProduct10(
    "id",
    "reserved_user_id",
    "name",
    "description",
    "reserved_from",
    "reserved_to",
    "job_id",
    "job_status",
    "executed_at",
    "canceled_at"
  )(reserveEntity => (
    reserveEntity.id,
    reserveEntity.reservedUserId,
    reserveEntity.name,
    reserveEntity.description,
    reserveEntity.reservedFrom,
    reserveEntity.reservedTo,
    reserveEntity.jobId,
    reserveEntity.jobStatus,
    reserveEntity.executedAt,
    reserveEntity.canceledAt
  ))

  implicit val decodeReserve: Decoder[ReserveEntity] = Decoder.forProduct10(
    "id",
    "reserved_user_id",
    "name",
    "description",
    "reserved_from",
    "reserved_to",
    "job_id",
    "job_status",
    "executed_at",
    "canceled_at"
  )((id: Int, reservedUserId: String, name: String, description: Option[String],
     reservedFrom: DateTime, reservedTo: DateTime,
     jobId: Option[String], jobStatus: Option[String],
     executedAt: Option[DateTime],
     canceldAt: Option[DateTime]) => {
    new ReserveEntity(
      id = id,
      reservedUserId = reservedUserId.toString,
      name = name.toString,
      reservedFrom = new DateTime(),
      reservedTo = new DateTime()
    )
  })
}
