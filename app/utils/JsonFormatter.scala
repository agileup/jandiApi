package utils

import java.sql.Timestamp

import models.Account
import play.api.libs.json.{JsResult, Json, JsValue, Format}

object JsonFormatter {
  def timestampToDateTime(t: Timestamp): String = t.toString()
  def dateTimeToTimestamp(dt: String): Timestamp = Timestamp.valueOf(dt)
  implicit val timestampFormat = new Format[Timestamp] {
    def writes(t: Timestamp): JsValue = Json.toJson(timestampToDateTime(t))
    def reads(json: JsValue): JsResult[Timestamp] = Json.fromJson[String](json).map(dateTimeToTimestamp)
  }
  implicit val accountFmt = Json.format[Account]
}
