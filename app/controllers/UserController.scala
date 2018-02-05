package controllers

import javax.inject._

import akka.actor.ActorSystem
import play.api.mvc._
import services.Users
import play.api.libs.json._

import scala.concurrent.ExecutionContext

case class UserModel(username:String, id:Int)
case class UserPassModel(username:String, password:String)

@Singleton
class UserController @Inject()(cc:ControllerComponents,  actorSystem:ActorSystem, users:Users)(implicit exec:ExecutionContext) extends AbstractController(cc) {

  implicit val userFormat: OFormat[UserModel] = Json.format[UserModel]
  implicit val userPassFormat: OFormat[UserPassModel] = Json.format[UserPassModel]

  def getUser: Action[AnyContent] = Action.async((req) => {
    val username = req.queryString("username").head

    users.getUser(username).map(z => Ok(Json.stringify(userFormat.writes(UserModel(z.username, z.id)))))
  })

  def addUser: Action[AnyContent] = Action.async((req) => {
    val user = userPassFormat.reads(req.body.asJson.get).get

    users.addUser(user.username, user.password).map(_ => Created)
  })

}
