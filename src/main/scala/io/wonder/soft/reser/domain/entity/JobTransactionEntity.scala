package io.wonder.soft.reser.domain.entity

import org.joda.time.DateTime

final case class JobTransactionEntity
(
  id: Int,
  reserveId: Int,
  jobId: String,
  jobName: String,
  jobStatus: String,
  executedAt: DateTime,
  finishedAt: DateTime,
  failedAt: Option[DateTime],
  failMessage: Option[String],
  canceledAt: Option[DateTime]
) {
  require(id > 0, "")
  require(reserveId > 0, "")
  require(jobId.nonEmpty, "")
  require(jobName.nonEmpty, "")
  require(jobStatus.nonEmpty, "")
  require(failedAt.isEmpty, "")
  require(failMessage.isEmpty, "")
  require(canceledAt.isEmpty, "")
}
