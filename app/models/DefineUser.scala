package models

import play.api.libs.json.{Json, OFormat}

case class DefineUser(username: String, password: String)

object DefineUser {
  implicit val format: OFormat[DefineUser] = Json.format[DefineUser]
}
