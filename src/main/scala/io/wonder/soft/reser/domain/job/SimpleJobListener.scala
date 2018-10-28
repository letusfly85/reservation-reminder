package io.wonder.soft.reser.domain.job

import org.quartz.{JobExecutionContext, JobExecutionException, JobListener}
import org.slf4j.LoggerFactory

class SimpleJobListener(listenerName: String) extends JobListener {

  val logger = LoggerFactory.getLogger(this.getName)

  override def getName: String = listenerName

  override def jobToBeExecuted(context: JobExecutionContext): Unit = {
    val description = context.getJobDetail.getDescription

    this.logger.info(description)
  }

  override def jobExecutionVetoed(context: JobExecutionContext): Unit = {
    val description = context.getJobDetail.getDescription

    this.logger.info(description)
  }

  override def jobWasExecuted(context: JobExecutionContext, jobException: JobExecutionException): Unit = {
    val jobName = context.getJobDetail().getKey().toString()

    jobException match {
      case null =>
        logger.info(s"FINISHED: ${jobName}")
      case _ =>
        logger.error(this.getName, jobException)
    }
  }

}
