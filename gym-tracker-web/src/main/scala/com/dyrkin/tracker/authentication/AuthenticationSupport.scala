package com.dyrkin.tracker.authentication

import com.dyrkin.tracker.authentication.strategies.UserPasswordStrategy
import com.dyrkin.tracker.core.service.UserService
import com.dyrkin.tracker.core.vo.WatchUserDetails
import com.dyrkin.tracker.web.ServicesAware
import org.scalatra.auth.{ScentryConfig, ScentrySupport}
import org.scalatra.ScalatraBase
import org.slf4j.LoggerFactory
import com.dyrkin.tracker.core.driver.AgnosticDriver.api._

/**
  * @author ihor zadyra
  */
trait AuthenticationSupport extends ScalatraBase with ScentrySupport[WatchUserDetails] {
  self: ServicesAware =>

  protected def fromSession = { case id: String => services.userService.getUserDetailsById(id.toLong).get  }
  protected def toSession   = { case usr: WatchUserDetails => services.userService.getUserDetailsById(usr.id).get.id.toString }

  protected val scentryConfig = new ScentryConfig {override val login = "/user/authenticate"}.asInstanceOf[ScentryConfiguration]

  val logger = LoggerFactory.getLogger(getClass)

  protected def requireLogin() = {
    if(!isAuthenticated) {
      redirect("/user/authenticate")
    }
  }

  override protected def configureScentry = {
    scentry.unauthenticated {
      scentry.strategies("UserPassword").unauthenticated()
    }
  }

  override protected def registerAuthStrategies = {
    scentry.register("UserPassword", app => new UserPasswordStrategy(app, services))
  }

}