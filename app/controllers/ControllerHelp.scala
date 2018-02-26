package controllers

import play.api.mvc.{AnyContent, Request}

import scala.util.Try

object ControllerHelp {
  def getTerm(req: Request[AnyContent]): Option[String] = Try(req.queryString("username").head).toOption

  def getIdTerm(req: Request[AnyContent]): Option[Int] = Try(req.queryString("id").head.toInt).toOption

}
