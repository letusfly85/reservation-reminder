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

  def generateClient(host: String, port: Int) = {
    val docker = DefaultDockerClient
      .builder
      .uri(URI.create(s"http://${host}:${port}"))
      // .dockerCertificates(new DockerCertificates(Paths.get("/Users/rohan/.docker/boot2docker-vm/")))
      .build

    docker
  }

  /**
    *
    * @param imageName
    * @param actorRef
    * @param host
    * @param port
    */
  def pullImage(imageName: String, actorRef: ActorRef, host: String, port: Int) = {
    val client = this.generateClient(host, port)
    val progressHandler = new DockerEventProgressHandler(actorRef)

    client.pull(imageName, progressHandler)
  }

  /**
    *
    * @param imageName
    * @param actorRef
    * @param host
    * @param port
    */
  def pushImage(imageName: String, actorRef: ActorRef, host: String, port: Int) = {
    val client = this.generateClient(host, port)
    val progressHandler = new DockerEventProgressHandler(actorRef)

    client.push(imageName, progressHandler)
  }

  /**
    *
    * @param handler
    */
  def runContainer(handler: ActorRef) = {
  }

}
