package controllers

import javax.inject._

import akka.actor.ActorSystem
import models.User
import play.api.mvc._
import services.Users
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future, Promise}

@Singleton
class UserController @Inject()(cc:ControllerComponents,  actorSystem:ActorSystem, users:Users)(implicit exec:ExecutionContext) extends AbstractController(cc) {

  implicit val residentFormat = Json.format[User]

  def getUser: Action[AnyContent] = Action.async((req) => {
//    val authHeader = req.headers.get("auth").getOrElse("failed")

    users.getUser("grant").map(z => Ok(Json.stringify(residentFormat.writes(z))))
  })

}
