package models

import play.api.libs.json.{JsValue, Json, OFormat}

case class PublicUser(username: String, id: Int)

object PublicUser {
  implicit val format: OFormat[PublicUser] = Json.format[PublicUser]

  implicit def publicUserToJson(u: PublicUser): JsValue = Json.toJson(u)
}
