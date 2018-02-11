package controllers

import javax.inject._

import akka.actor.ActorSystem
import models.{DefineUser, User}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.Users

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

@Singleton
class UserController @Inject()(cc: ControllerComponents, actorSystem: ActorSystem, users: Users)(implicit exec: ExecutionContext) extends AbstractController(cc) {

    def getResponse(y: Try[Option[User]]): Result =
      y.fold(
        _ => InternalServerError.apply(""),
        fb => fb.fold(
          NotFound.apply(""))(
          fb => Ok.apply(Json.stringify(fb.toPublic))))

  def getTerm(req: Request[AnyContent]): String = req.queryString("username").head

  def getUserFromRequest(req: Request[AnyContent]): Future[Result] = users.getUser(getTerm(req)).map(getResponse)

  def getUser: Action[AnyContent] = Action.async(x => getUserFromRequest(x))


  def parseUserFromBody(req: Request[AnyContent]): Option[User] = {
    val x = req.body.asJson
    Json.reads[User].reads(x.get).asOpt
  }

  def getAddUserResponse(y: Try[Boolean]): Result = {
    y.fold(
      _ => InternalServerError,
      f => if (f) {
        Created
      } else {
        BadRequest
      }
    )
  }

  def addUserToDb(u: DefineUser): Future[Result] = users.addUser(u.username, u.password).map(getAddUserResponse)

  def addUser(): Action[JsValue] = Action.async(parse.json) { (req: Request[JsValue]) =>
    val op = req.body.validate[DefineUser].asOpt
    if (op.nonEmpty) {
      addUserToDb(op.get)
    } else {
      Future.successful("").map(_ => BadRequest)
    }
  }
}
