package services

import javax.inject._

import models.User
import scalikejdbc._

import scala.concurrent.Future
import scala.util.Try

trait Users {
  def getUser(username: String): Future[Try[Option[User]]]

  def getUserById(userId: Int): Future[Try[Option[User]]]

  def addUser(username: String, password: String): Future[Try[Boolean]]
}

@Singleton
class UserService @Inject()() extends Users {
  val failed = User("", "", -1, enabled = false)

  val userMap: WrappedResultSet => User = (rs: WrappedResultSet) => User(
    rs.string("username"),
    rs.string("password"),
    rs.int("id"),
    rs.boolean("enabled")
  )

  private def getUserByNameSync(username: String): Try[Option[User]] = {
    Try {
      DB readOnly { implicit s =>
        sql"""SELECT * FROM users WHERE username = $username""".map(userMap).single.apply
      }
    }
  }

  private def getUserByIdSync(userId: Int): Try[Option[User]] = {
    Try {
      DB readOnly { implicit s =>
        sql"""SELECT * FROM users WHERE id = $userId""".map(userMap).single.apply
      }
    }
  }

  private def addUserSync(str: String, str1: String): Try[Boolean] = {
    Try{
      val res = DB autoCommit { implicit s =>
        sql"""INSERT INTO users (username, password) VALUES ($str, $str1)""".update.apply
      }

      if (res == 0) {
        false
      } else {
        true
      }
    }
  }

  def addUser(username: String, password: String): Future[Try[Boolean]] = {
    Future.successful(addUserSync(username, password))
  }

  def getUser(username: String): Future[Try[Option[User]]] = {
    Future.successful(getUserByNameSync(username))
  }

  def getUserById(userId: Int): Future[User] = {
    Future.successful(getUserByIdSync(userId))
  }
}
