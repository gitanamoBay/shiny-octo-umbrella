package models

import play.api.libs.json.{JsValue, Json, OFormat}

case class User(username: String, password: String, id: Int, enabled: Boolean) {
  implicit val format: OFormat[User] = Json.format[User]

  def toPublic: PublicUser = PublicUser(username, id)
}

case class PublicUser(username: String, id: Int) {
  implicit val format: OFormat[PublicUser] = Json.format[PublicUser]

  implicit def publicUserToJson(u: PublicUser): JsValue = Json.toJson(u)
}


case class DefineUser(username: String, password: String) {
  implicit val format: OFormat[DefineUser] = Json.format[DefineUser]
}
