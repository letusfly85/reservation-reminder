package io.wonder.soft.reser.domain.job

import akka.event.slf4j.Logger
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.slf4j.LoggerFactory

class SimpleJob extends org.quartz.Job {
  val logger = LoggerFactory.getLogger(getClass.getName)

  def execute(context: JobExecutionContext): Unit = {
    logger.info(context.getFireTime.toString)
  }


}
