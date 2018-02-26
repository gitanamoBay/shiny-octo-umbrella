package services

import javax.inject._

import models.Change
import org.apache.commons.lang3.NotImplementedException
import scalikejdbc._

import scala.concurrent.Future
import scala.util.Try

trait Changes {
  def getAllChanges(filter: => String, skip: Int, take: Int): Future[Try[Option[Array[Change]]]]

  def addChange(change: Change): Future[Try[Option[Boolean]]]

  def updateChange(change: Change): Future[Try[Option[Boolean]]]

}

@Singleton
class ChangeService @Inject()() extends Changes {

  def getAllChanges(filter: => String, skip: Int, take: Int): Future[Try[Option[Array[Change]]]] = {
    Future.successful(Try {
      throw NotImplementedException
    })
  }

  override def addChange(change: Change): Future[Try[Option[Boolean]]] = Future.successful(Try {
    throw NotImplementedException
  })

  override def updateChange(change: Change): Future[Try[Option[Boolean]]] = Future.successful(Try {
    throw NotImplementedException
  })
}