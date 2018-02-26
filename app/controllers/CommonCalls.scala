package controllers

import play.api.mvc._

import scala.util.Try


object CommonCalls {
  def getStringTerm(req: Request[AnyContent], name:String): Option[String] = req.queryString(name).headOption
  def getIntTerm(req: Request[AnyContent], name:String): Option[Int] = req.queryString(name).headOption.map(_.toInt)

  implicit def getResponse[A](y: Try[Option[A]])(f: A => String): Result =
    y.fold(
      _ => Results.InternalServerError(""),
      fb => fb.fold(
        Results.NotFound(""))(
        fb => Results.Ok(f(fb))))
}
