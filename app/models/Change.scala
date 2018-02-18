package models

import org.joda.time.DateTime
import play.api.libs.json.{Json, OFormat}

case class Change(id: Int,
                  userId: Int,
                  severity: Int,
                  status: Short,
                  data: String,
                  dateLogged: DateTime,
                  dateStarted: Option[DateTime],
                  dateFinished: Option[DateTime]) {
  implicit val format: OFormat[User] = Json.format[User]
}
