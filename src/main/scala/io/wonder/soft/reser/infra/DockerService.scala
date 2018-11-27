package io.wonder.soft.reser.infra

import com.spotify.docker.client.DefaultDockerClient
import java.net.URI

import akka.actor.ActorRef

/**
  *
  *
  * refs: https://github.com/spotify/docker-client/blob/master/docs/user_manual.md#creating-a-docker-client
  */
class DockerService {

  private var client: DefaultDockerClient = _

  def generateClient(host: String = "localhost", port: Int = 2375) = {
    val docker = DefaultDockerClient
      .builder
      .uri(URI.create(s"http://${host}:${port}"))
      // .dockerCertificates(new DockerCertificates(Paths.get("/Users/rohan/.docker/boot2docker-vm/")))
      .build

    this.client = docker
    docker
  }

  /**
    *
    * @param imageName
    * @param actorRef
    */
  def pullImage(imageName: String, actorRef: ActorRef) = {
    val progressHandler = new DockerEventProgressHandler(actorRef)

    this.client.pull(imageName, progressHandler)
  }

  /**
    *
    * @param imageName
    * @param actorRef
    */
  def pushImage(imageName: String, actorRef: ActorRef) = {
    val progressHandler = new DockerEventProgressHandler(actorRef)

    this.client.push(imageName, progressHandler)
  }

  /**
    *
    * @param handler
    */
  def runContainer(handler: ActorRef) = {
  }

}
