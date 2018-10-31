package io.wonder.soft.reser.domain.entity

import java.util.Date

final case class JobTransactionEntity (
  id: Int,
  reserveId: Int,
  jobId: String,
  jobName: String,
  jobStatus: String,
  executedAt: Date,
  finishedAt: Date,
  failedAt: Option[Date],
  failMessage: Option[String],
  canceledAt: Option[Date]
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
