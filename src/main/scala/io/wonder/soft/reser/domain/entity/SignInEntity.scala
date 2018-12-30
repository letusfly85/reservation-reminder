package io.wonder.soft.reser.domain.entity


import io.circe.{Decoder, Encoder}

final case class SignInEntity
(
  userId: String,
  password: String
) {
  require(userId.nonEmpty , "userId.empty")
  require(password.nonEmpty, "password.empty")
}

object SignInEntity {

  implicit def encodeSignIn: Encoder[SignInEntity] = Encoder.forProduct2(
    "user_id",
    "password"
  )(reserveEntity => (
    reserveEntity.userId,
    reserveEntity.password
  ))

  implicit val decodeSignIn: Decoder[SignInEntity] = Decoder.forProduct2(
    "user_id",
    "password"
  )((userId: String,
     password: String) => {
    new SignInEntity(
      userId = userId,
      password = password
    )
  })
}
