package com.dyrkin.tracker.authentication


import com.dyrkin.tracker.authentication.strategies.UserPasswordStrategy
import com.dyrkin.tracker.core.service.UserService
import com.dyrkin.tracker.core.vo.WatchUserDetails
import org.scalatra.auth.{ScentryConfig, ScentrySupport}
import org.scalatra.ScalatraBase
import org.slf4j.LoggerFactory
import com.dyrkin.tracker.core.driver.AgnosticDriver.api._


trait AuthenticationSupport extends ScalatraBase with ScentrySupport[WatchUserDetails] {
  self: ScalatraBase =>

  implicit val db = Database.forConfig("db")
  val userService = new UserService

  protected def fromSession = { case id: String => userService.getUserDetailsById(id.toLong).get  }
  protected def toSession   = { case usr: WatchUserDetails => userService.getUserDetailsById(usr.id).get.id.toString }

  protected val scentryConfig = new ScentryConfig {override val login = "/user/authenticate"}.asInstanceOf[ScentryConfiguration]

  val logger = LoggerFactory.getLogger(getClass)

  protected def requireLogin() = {
    if(!isAuthenticated) {
      redirect("/user/authenticate")
    }
  }

  /**
    * If an unauthenticated user attempts to access a route which is protected by Scentry,
    * run the unauthenticated() method on the UserPasswordStrategy.
    */
  override protected def configureScentry = {
    scentry.unauthenticated {
      scentry.strategies("UserPassword").unauthenticated()
    }
  }

  /**
    * Register auth strategies with Scentry. Any controller with this trait mixed in will attempt to
    * progressively use all registered strategies to log the user in, falling back if necessary.
    */
  override protected def registerAuthStrategies = {
    scentry.register("UserPassword", app => new UserPasswordStrategy(app))
  }

}