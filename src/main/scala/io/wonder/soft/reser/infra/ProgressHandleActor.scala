package io.wonder.soft.reser.infra

import akka.actor.Actor
import com.spotify.docker.client.messages.ProgressMessage
import org.slf4j.LoggerFactory

class ProgressHandleActor extends Actor {
  val logger = LoggerFactory.getLogger(getClass)

  def receive = {
    case message: ProgressMessage =>
      logger.info(message.progress())
  }

}
