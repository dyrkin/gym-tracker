package com.dyrkin.tracker.authentication

import com.dyrkin.tracker.authentication.strategies.UserPasswordStrategy
import com.dyrkin.tracker.core.repository.Tables.User
import com.dyrkin.tracker.core.util._
import com.dyrkin.tracker.web.ServicesAware
import org.scalatra.ScalatraBase
import org.scalatra.auth.{ScentryConfig, ScentrySupport}
import org.slf4j.LoggerFactory

/**
  * @author ihor zadyra
  */
trait AuthenticationSupport extends ScalatraBase with ScentrySupport[User] {
  self: ServicesAware =>

  protected def fromSession = { case id: String => services.userService.getUserDetailsById(id.toLong).get  }
  protected def toSession   = { case user: User =>
    val userId = user.id.getOrElse(noId)
    services.userService.getUserDetailsById(userId).flatMap(_.id).getOrElse(noId).toString

  }

  protected val scentryConfig = new ScentryConfig {override val login = "/user/authenticate"}.asInstanceOf[ScentryConfiguration]

  val logger = LoggerFactory.getLogger(getClass)

  protected def requireLogin() = {
    if (!isAuthenticated) {
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