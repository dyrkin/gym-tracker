package com.dyrkin.tracker.authentication.strategies


import com.dyrkin.tracker.core.service.{ServicesT, Services, UserService, ProgramService}
import com.dyrkin.tracker.core.vo.WatchUserDetails
import org.scalatra.ScalatraBase
import org.scalatra.auth.ScentryStrategy
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}

class UserPasswordStrategy(protected val app: ScalatraBase, implicit val services: ServicesT)(implicit request: HttpServletRequest, response: HttpServletResponse)
  extends ScentryStrategy[WatchUserDetails] {


  override def name: String = "UserPassword"

  private def email = app.params.getOrElse("email", "")
  private def password = app.params.getOrElse("password", "")

  override def isValid(implicit request: HttpServletRequest) = {

    email != "" && email.contains("@") && password != ""
  }

  def authenticate()(implicit request: HttpServletRequest, response: HttpServletResponse): Option[WatchUserDetails] = {
    services.userService.userByEmailAndPassword(email, password)
  }
}
