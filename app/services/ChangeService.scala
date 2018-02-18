package services

import javax.inject._

import models.Change
import scalikejdbc._

import scala.concurrent.Future
import scala.util.Try

trait Changes {

}

@Singleton
class ChangeService @Inject()() extends Changes{

}