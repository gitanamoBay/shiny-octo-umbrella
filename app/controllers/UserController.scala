package controllers

import javax.inject._

import akka.actor.ActorSystem
import models.User
import play.api.libs.json.Json
import play.api.mvc._
import services.Users

import scala.concurrent.ExecutionContext
import scala.util.Try

case class UserModel(username: String, id: Int)

case class UserPassModel(username: String, password: String)

@Singleton
class UserController @Inject()(cc: ControllerComponents, actorSystem: ActorSystem, users: Users)(implicit exec: ExecutionContext) extends AbstractController(cc) {

  //  def funx(y: Try[Option[User]]): Result = {
  //    y.fold(fa => InternalServerError, fb => fb.fold(NotFound).)
  //  }

  def getUser: Action[AnyContent] = Action.async((req) => {
    //    val x: Future[Try[Option[User]]] =
    //      users.getUser(username).map{x => funx(x) }
    //    users.getUser(username).map(s => s.fold[Status]())
    val username = req.queryString("username").head
    users.getUser(username)
      .map(f => f.fold(
        _ => InternalServerError.apply(""),
        fb => fb.fold(
          NotFound.apply(""))(
          fb => Ok.apply(Json.stringify(fb.toPublic))))
      )
  })


  def addUser: Action[AnyContent] = Action.async((req) => {
    val user = User("", "", 0, true)
    users.addUser(user.username, user.password).map(_ => Created)
  })

}
