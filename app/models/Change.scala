package models


import org.joda.time.DateTime
import play.api.libs.json.{JsValue, Json, OFormat}
import org.joda.time.format.ISODateTimeFormat
import play.api.libs.json._


case class Change(id: Int,
                  userId: Int,
                  severity: Int,
                  status: Short,
                  data: JsValue,
                  date: DateTime)

object Change {
  private val fmt = ISODateTimeFormat.dateTime

  implicit val isoDateTimeWrites: Writes[DateTime] = (o: DateTime) => JsString(fmt.print(o))

  implicit val isoDateTimeReads: Reads[DateTime] = {
    case JsString(value) => JsSuccess(fmt.parseDateTime(value))
    case _ => JsError()
  }

  implicit val format: OFormat[Change] = Json.format[Change]

  implicit def changeToJson(u: Change): JsValue = Json.toJson(u)
}
