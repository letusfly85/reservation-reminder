package io.wonder.soft.reser.domain.job

import org.quartz._
import org.quartz.impl.StdSchedulerFactory
import org.quartz.SimpleScheduleBuilder.simpleSchedule

class SimpleJobExecutor {

  val job: JobDetail =
    JobBuilder.newJob(classOf[SimpleJob])
      .withIdentity("job1", "group1")
      .build

  val trigger: Trigger =
    TriggerBuilder
      .newTrigger.withIdentity("trigger1", "group1")
      .startNow
      .withSchedule(simpleSchedule.withIntervalInMilliseconds(1000).repeatForever)
      .build

  var scheduler: Scheduler = _

  def setCommand(command: String): Unit = {
    job.getJobDataMap.put("command", command)
  }

  def startSchedule() = {
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
