package io.wonder.soft.reser.domain.job

import org.quartz.{JobDataMap, JobExecutionContext}
import org.slf4j.LoggerFactory

class SimpleJob extends org.quartz.Job {
  val logger = LoggerFactory.getLogger(getClass.getName)

  def execute(context: JobExecutionContext): Unit = {
    val jobDataMap: JobDataMap = context.getJobDetail().getJobDataMap()
    logger.info(jobDataMap.getString("command"))
    logger.info(context.getFireTime.toString)
  }


}
