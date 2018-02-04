package services

import javax.inject._
import models.User
import scalikejdbc._
import scala.concurrent.Future

trait Users {
  def getUser(username:String):Future[User]
  def getUserById(userId:Int):Future[User]
  def addUser(username:String, password:String):Future[Boolean]
}

@Singleton
class UserService @Inject()() extends Users {
  val failed = User("", "", -1, enabled = false)

  val userMap: WrappedResultSet => User = (rs:WrappedResultSet) => User(
    rs.string("username"),
    rs.string("password"),
    rs.int("id"),
    rs.boolean("enabled")
  )

  //this is bad, sql injection! :( todo:parameterize, can be asynced in future
  private def getUserByNameSync(username:String):User = {
    val user = DB readOnly{ implicit s =>
      sql"""SELECT * FROM change_users WHERE username = $username""".map(userMap).single.apply
    }

    user.getOrElse(failed)
  }

  private def getUserByIdSync(userId:Int):User = {
    val user = DB readOnly{ implicit s => sql"""SELECT * FROM change_users WHERE id = $userId""".map(userMap).single().apply()
    }

    user.getOrElse(failed)
  }

  private def addUserSync(str: String, str1: String):Boolean = {
    DB autoCommit { implicit s =>
      sql"""INSERT INTO change_user (username, password) VALUES (${str}, ${str1})""".execute().apply()
    }
  }

  def addUser(username:String, password:String):Future[Boolean] = {
    Future.successful(addUserSync(username, password))
  }

  def getUser(username:String):Future[User] = {
    Future.successful(getUserByNameSync(username))
  }

  def getUserById(userId:Int):Future[User] = {
    Future.successful(getUserByIdSync(userId))
  }
}
