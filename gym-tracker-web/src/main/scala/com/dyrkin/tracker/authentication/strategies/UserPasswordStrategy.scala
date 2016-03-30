package com.dyrkin.tracker.authentication.strategies


import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.dyrkin.tracker.core.repository.Tables.User
import com.dyrkin.tracker.core.service.ServicesT
import com.dyrkin.tracker.core.util.OptionString
import org.scalatra.ScalatraBase
import org.scalatra.auth.ScentryStrategy

/**
  * @author ihor zadyra
  */
class UserPasswordStrategy(protected val app: ScalatraBase, implicit val services: ServicesT)(implicit request: HttpServletRequest, response: HttpServletResponse)
  extends ScentryStrategy[User] {


  override def name: String = "UserPassword"

  private def email = app.params.get("email").noneIfEmpty

  private def password = app.params.get("password").noneIfEmpty

  override def isValid(implicit request: HttpServletRequest) = {
    email.isDefined && email.exists(_.contains("@")) && password.isDefined
  }

  def authenticate()(implicit request: HttpServletRequest, response: HttpServletResponse): Option[User] = {
    for {
      e <- email
      p <- password
      userDetails <- services.userService.userByEmailAndPassword(e, p)
    } yield userDetails
  }
}
