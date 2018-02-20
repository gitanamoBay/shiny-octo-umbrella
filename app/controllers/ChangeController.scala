package controllers

import javax.inject._

import akka.actor.ActorSystem
import models.Change
import org.joda.time.DateTime
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.Changes

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ChangeController @Inject() (cc: ControllerComponents, actorSystem: ActorSystem, changes: Changes)(implicit exec:ExecutionContext) extends AbstractController(cc) {

  def getExample(): Action[AnyContent] = Action.async { x =>
    val jsValue: JsValue = Json.parse("{\"val\" : \"x\"}")
    val change = Change(
      1, 1, 1, 1, jsValue, DateTime.now)

    Future.successful("").map(_ => Ok(Json.stringify(change)))
  }
}
