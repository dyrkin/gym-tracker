package com.dyrkin.tracker.authentication.strategies


import com.dyrkin.tracker.core.service.{UserService, ProgramService}
import com.dyrkin.tracker.core.vo.WatchUserDetails
import org.scalatra.ScalatraBase
import org.scalatra.auth.ScentryStrategy
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
import com.dyrkin.tracker.core.driver.AgnosticDriver.api._

class UserPasswordStrategy(protected val app: ScalatraBase)(implicit request: HttpServletRequest, response: HttpServletResponse)
  extends ScentryStrategy[WatchUserDetails] {

  implicit val db = Database.forConfig("db")
  val userService = new UserService

  override def name: String = "UserPassword"

  private def email = app.params.getOrElse("email", "")

  private def password = app.params.getOrElse("password", "")


  /** *
    * Determine whether the strategy should be run for the current request.
    */
  override def isValid(implicit request: HttpServletRequest) = {

    email != "" && password != ""
  }

  /**
    * In real life, this is where we'd consult our data store, asking it whether the user credentials matched
    * any existing user. Here, we'll just check for a known login/password combination and return a user if
    * it's found.
    */
  def authenticate()(implicit request: HttpServletRequest, response: HttpServletResponse): Option[WatchUserDetails] = {
    userService.userByEmailAndPassword(email, password)
  }

  /**
    * What should happen if the user is currently not authenticated?
    */
  override def unauthenticated()(implicit request: HttpServletRequest, response: HttpServletResponse) {
   // app.redirect("/user/logout")
  }

}
