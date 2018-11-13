package io.wonder.soft.reser.domain.entity

object ErrorResponseEntity {

  case class NotFoundEntity(path: String, method: String, message: String, cause: Option[String] = None, targetResource: String)

  case class AlreadyExistsEntity(path: String, method: String, message: String, cause: Option[String] = None, targetResource: String)

}
