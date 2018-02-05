package models

import play.api.libs.json.Json

case class User(username: String, password: String, id: Int, enabled: Boolean) {
  def toPublic: PublicUser = PublicUser(username, id)
}

case class PublicUser(username: String, id: Int)

object PublicUser{
  implicit val format = Json.format[PublicUser]

  implicit def publicUserToJson(u: PublicUser) = Json.toJson(u)
}