package controllers

import java.sql.Timestamp

import play.api.mvc._
import play.api.libs.json._
import models._

object Application extends Controller {
  import utils.JsonFormatter._

  def listAccounts(offset: Int, limit: Int) = Action {
    val _offset: Int = offset
    val _limit: Int = limit
    val json: JsValue = Json.toJson(Accounts.getAll(_offset, _limit))
    Ok(json)

  }

  def getAccount(id: Long) = Action { request =>
/*
    val body: AnyContent = request.body
    val textBody: Option[String] = body.asText
*/
    val json: JsValue = Json.toJson(Accounts.get(id))
    Ok(json)

  }

  def saveAccounts = Action(parse.text) { request =>
    val textBody: String = request.body

    val now: Timestamp = new Timestamp(new java.util.Date().getTime)

    val accountId: Long = Accounts.save(Account(
      id = 0,
      name = "Aiden",
      password = "passpasspass",
      createdAt = now
    ))

    val json: JsValue = Json.parse("{\"id\" : "+accountId+"}")
    Ok(json)
  }

}