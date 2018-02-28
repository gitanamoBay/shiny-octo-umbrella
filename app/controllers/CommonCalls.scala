package controllers

import play.api.mvc._

import scala.util.Try

object CommonCalls {
  def getStringTerm(req: Request[AnyContent], name:String): Option[String] = req.queryString(name).headOption
  def getStringTerm(req: Request[AnyContent], name:String, default:String): String = req.queryString(name).headOption.getOrElse(default)

  def getIntTerm(req: Request[AnyContent], name:String): Option[Int] = req.queryString(name).headOption.map(_.toInt)
  def getIntTerm(req: Request[AnyContent], name:String, default:Int): Int = req.queryString(name).headOption.map(_.toInt).getOrElse(default)

  def getTerm[A](req: Request[AnyContent], name:String, f: String => A, default:A): Option[A] = req.queryString(name).headOption.map(f(_))
  def getTerm[A](req: Request[AnyContent], name:String, f: String => A, default:A): A = req.queryString(name).headOption.map(f(_)).getOrElse(default)

  implicit def getResponse[A](y: Try[Option[A]], iseMessage:String = "", notFoundMessage:String = "")(f: A => String): Result =
    y.fold(
      _ => Results.InternalServerError(iseMessage),
      fb => fb.fold(
        Results.NotFound(notFoundMessage))(
        fb => Results.Ok(f(fb))))
}
