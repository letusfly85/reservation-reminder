package io.wonder.soft.reser.domain.job

import java.io.{File, FileOutputStream}
import java.util.UUID

import org.quartz.{JobDataMap, JobExecutionContext}
import org.slf4j.LoggerFactory

import scala.sys.process._
import scala.util.{Failure, Success, Try}

class SimpleJob extends org.quartz.Job {
  val logger = LoggerFactory.getLogger(getClass.getName)

  def execute(context: JobExecutionContext): Unit = {
    val jobDataMap: JobDataMap = context.getJobDetail().getJobDataMap()

    Try {
      val command: String = jobDataMap.getString("command")
      logger.info(context.getFireTime.toString)

      val executeFileName = s"/tmp/${UUID.randomUUID().toString}.sh"
      val executeFile = new File(executeFileName)

      val os = new FileOutputStream(executeFile)
      os.write(command.getBytes)
      os.close()

      val process = Process(s"bash ${executeFileName}")
      val result = process.lineStream.foldLeft(List.empty[String]) { (acc, line) =>
        line :: acc
      }

      result.mkString("\n")
    } match {
      case Success(result) => logger.info(result)
      case Failure(ex) => logger.error("", ex)
    }
  }


}
