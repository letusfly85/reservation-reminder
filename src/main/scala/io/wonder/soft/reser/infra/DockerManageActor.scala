package io.wonder.soft.reser.infra

import akka.actor.{Actor, Props}

class DockerManageActor extends Actor {

  def receive = {
    case ('pull, imageName: String) =>
      val dockerService = new DockerService()
      dockerService.generateClient()

      val actor = context.actorOf(Props[ProgressHandleActor])
      dockerService.pullImage(imageName, actor)
  }

}
