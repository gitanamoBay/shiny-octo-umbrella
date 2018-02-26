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

  def skipValidation(skipVal: Request[AnyContent]): Either[String,Int] = Either(None,0)
  def takeValidation(skipVal: Request[AnyContent]): Either[String,Int] = Either(None,0)

  def getExample: Action[AnyContent] = Action.async { _ =>
    val jsValue: JsValue = Json.parse("{\"val\" : \"x\"}")
    val change = Change(1, 1, 1, 1, jsValue, DateTime.now)

    Future.successful(Ok(Json.stringify(change)))
  }

  def getById: Action[AnyContent] = Action.async { x =>
    CommonCalls.getIntTerm(x, "id").fold(Future.successful(BadRequest("")))(
      y => changes.getById(y).map(y => CommonCalls.getResponse(y)(x => Json.stringify(x))))
  }

  def get: Action[AnyContent] = Action.async { x =>

    val s = skipValidation(x)
    val t = takeValidation(x)

    if (s.isLeft) Future.successful(BadRequest(s.left.get))
    else if (t.isLeft) Future.successful(BadRequest(t.left.get))
    else changes.getAllChanges("", s.right.get, t.right.get).map(_ => BadRequest(""))
  }
}
