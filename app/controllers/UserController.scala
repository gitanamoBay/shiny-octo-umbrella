package controllers

import javax.inject._

import akka.actor.ActorSystem
import models.User
import play.api.libs.json.Json
import play.api.mvc._
import services.Users

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

@Singleton
class UserController @Inject()(cc: ControllerComponents, actorSystem: ActorSystem, users: Users)(implicit exec: ExecutionContext) extends AbstractController(cc) {

    implicit def getResponse(y: Try[Option[User]]): Result =
      y.fold(
        _ => InternalServerError.apply(""),
        fb => fb.fold(
          NotFound.apply(""))(
          fb => Ok.apply(Json.stringify(fb.toPublic))))

  implicit def getTerm(req: Request[AnyContent]): String = req.queryString("username").head

  implicit def getUserFromRequest(req: Request[AnyContent]): Future[Result] = users.getUser(getTerm(req)).map(getResponse)

  def getUser: Action[AnyContent] = Action.async(x => getUserFromRequest(x))

  def addUser: Action[AnyContent] = Action.async((req) => {
    val x = req.body.asJson.get

    val v = Json.reads[User].reads(x)
    val u = v.get
    users.addUser(u.username, u.password).map(_ => Created)
  })

}
