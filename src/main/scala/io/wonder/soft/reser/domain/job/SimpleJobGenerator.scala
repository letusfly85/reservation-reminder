package io.wonder.soft.reser.domain.job

import java.util.Date

import org.quartz._
import org.quartz.SimpleScheduleBuilder.simpleSchedule

object SimpleJobGenerator {

  def generateJob(name: String, groupName: String, command: String) = {
    val job: JobDetail =
      JobBuilder.newJob(classOf[SimpleJob])
        .withIdentity(name, groupName)
        .build
    job.getJobDataMap.put("command", command)

    job
  }

  def generateTrigger(triggerName: String, groupName: String, startTime: Date, repeatCount: Int, intervalSecond: Int) = {
    val schedule = repeatCount match {
      case -1 => simpleSchedule().withIntervalInSeconds(intervalSecond).repeatForever()
      case 1 => simpleSchedule.withIntervalInSeconds(intervalSecond).withRepeatCount(1)
      case _ => simpleSchedule.withIntervalInSeconds(intervalSecond).withRepeatCount(repeatCount)
    }

    val trigger: Trigger =
      TriggerBuilder
        .newTrigger.withIdentity(triggerName, groupName)
        .startAt(startTime)
        .withSchedule(schedule)
        .build

    trigger
  }

}
