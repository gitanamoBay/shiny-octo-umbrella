package services

import javax.inject._

import models.Change
import scalikejdbc._

import scala.concurrent.Future
import scala.util.Try

trait Changes {
  def getById(y: Int): Future[Try[Option[Change]]]

}

@Singleton
class ChangeService @Inject()() extends Changes{



  def getById(y: Int): Future[Try[Option[Change]]] = {
    Future.successful()
  }
}