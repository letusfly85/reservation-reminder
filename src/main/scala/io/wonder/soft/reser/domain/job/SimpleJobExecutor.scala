package io.wonder.soft.reser.domain.job

import org.quartz._
import org.quartz.impl.StdSchedulerFactory
import org.quartz.SimpleScheduleBuilder.simpleSchedule

class SimpleJobExecutor {

  val job: JobDetail =
    JobBuilder.newJob(classOf[SimpleJob])
      .withIdentity("job1", "group1")
      .build

  job.getJobDataMap.put("command", "my command!")

  val trigger: Trigger =
    TriggerBuilder
      .newTrigger.withIdentity("trigger1", "group1")
      .startNow
      .withSchedule(simpleSchedule.withIntervalInMilliseconds(1000).repeatForever)
      .build

  var scheduler: Scheduler = _
  try {
    scheduler = StdSchedulerFactory.getDefaultScheduler()
    scheduler.scheduleJob(job, trigger)
    scheduler.start
  } catch {
    case e: Exception =>
      throw new RuntimeException(e)
  }

}
