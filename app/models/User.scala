package models

import play.api.libs.json.{Json, OFormat}

case class User(username: String, password: String, id: Int, enabled: Boolean) {
  implicit val format: OFormat[User] = Json.format[User]

  def toPublic: PublicUser = PublicUser(username, id)
}