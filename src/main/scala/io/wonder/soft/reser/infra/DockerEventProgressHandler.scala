package io.wonder.soft.reser.infra

import akka.actor.ActorRef
import com.spotify.docker.client.ProgressHandler
import com.spotify.docker.client.messages.ProgressMessage

class DockerEventProgressHandler(actorRef: ActorRef) extends ProgressHandler {

  override def progress(message: ProgressMessage): Unit = {
    actorRef ! message
  }

}
