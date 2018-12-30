package io.wonder.soft.reser.domain.entity

import com.softwaremill.session.{SessionSerializer, SingleValueSessionSerializer}

import scala.util.Try

case class AuthSession(userId: String)

object AuthSession {
  implicit def serializer: SessionSerializer[AuthSession, String] =
    new SingleValueSessionSerializer(_.userId,
      (un: String) =>
        Try {
          AuthSession(un)
        })
}
