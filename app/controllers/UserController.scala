package controllers

import javax.inject._

import akka.actor.ActorSystem
import models.User
import play.api.mvc._
import services.Users
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

case class UserModel(username: String, id: Int)

case class UserPassModel(username: String, password: String)

@Singleton
class UserController @Inject()(cc: ControllerComponents, actorSystem: ActorSystem, users: Users)(implicit exec: ExecutionContext) extends AbstractController(cc) {

  def getUser: Action[AnyContent] = Action.async((req) => {
    val username = req.queryString("username").head

    val x: Future[Try[Option[User]]] = users.getUser(username)
    x.map(z => Ok(Json.toJson(z.get.get.toPublic)))
  })

  def addUser: Action[AnyContent] = Action.async((req) => {
    val user = User("","", 0, true)
    users.addUser(user.username, user.password).map(_ => Created)
  })

}
