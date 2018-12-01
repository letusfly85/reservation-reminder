package io.wonder.soft.reser.infra

import com.spotify.docker.client.DefaultDockerClient
import java.net.URI

import akka.actor.ActorRef
import com.spotify.docker.client.messages.ContainerConfig

/**
  *
  *
  * ```/etc/sysconfig/docker
  * # The max number of open files for the daemon itself, and all
  * # running containers.  The default value of 1048576 mirrors the value
  * # used by the systemd service unit.
  * DAEMON_MAXFILES=1048576
  *
  * # Additional startup options for the Docker daemon, for example:
  * # OPTIONS="--ip-forward=true --iptables=true"
  * # By default we limit the number of open files per container
  * OPTIONS="--default-ulimit nofile=1024:4096 -H tcp://0.0.0.0:2376 -H unix:///var/run/docker.sock"
  *
  * # How many seconds the sysvinit script waits for the pidfile to appear
  * # when starting the daemon.
  * DAEMON_PIDFILE_TIMEOUT=10
  * ```
  *
  * refs: https://github.com/spotify/docker-client/blob/master/docs/user_manual.md#creating-a-docker-client
  */
class DockerService {

  private var client: DefaultDockerClient = _

  def generateClient(host: String = "0.0.0.0", port: Int = 2375) = {
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
  def runContainer(imageName: String, command: String, options: Map[String, String], actorRef: ActorRef) = {
    val containerConfig = ContainerConfig.builder().image(imageName).cmd(command).build()

    val result = this.client.createContainer(containerConfig)
    result.id()
  }

}
