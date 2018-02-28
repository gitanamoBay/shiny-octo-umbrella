package models

import org.joda.time.DateTime
import play.api.libs.json.{JsValue, Json, OFormat}
import org.joda.time.format.ISODateTimeFormat
import play.api.libs.json._

case class ChangeSummary()

object ChangeSummary{
    private val fmt = ISODateTimeFormat.dateTime

    implicit val isoDateTimeWrites: Writes[DateTime] = (o: DateTime) => JsString(fmt.print(o))

    implicit val isoDateTimeReads: Reads[DateTime] = {
      case JsString(value) => JsSuccess(fmt.parseDateTime(value))
      case _ => JsError()
    }

    implicit val format: OFormat[ChangeSummary] = Json.format[ChangeSummary]

    implicit def summaryToJson(u: ChangeSummary): JsValue = Json.toJson(u)
}

