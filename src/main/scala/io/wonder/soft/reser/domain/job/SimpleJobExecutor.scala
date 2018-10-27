package io.wonder.soft.reser.domain.job

import org.quartz._
import org.quartz.impl.StdSchedulerFactory

object SimpleJobExecutor {

  var scheduler: Scheduler = _

  def startSchedule(job: JobDetail, trigger: Trigger) = {
    try {
      scheduler = StdSchedulerFactory.getDefaultScheduler()
      scheduler.scheduleJob(job, trigger)
      scheduler.start
    } catch {
      case e: Exception =>
        throw new RuntimeException(e)
    }
  }

}
