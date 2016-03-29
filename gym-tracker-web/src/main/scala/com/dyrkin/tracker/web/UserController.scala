package com.dyrkin.tracker.web

import com.dyrkin.tracker.authentication.AuthenticationSupport
import com.dyrkin.tracker.core.util._
import com.dyrkin.tracker.web.model.JsonModels
import org.scalatra.json.JacksonJsonSupport

/**
  * @author eugene zadyra
  */
trait UserController extends JacksonJsonSupport with AuthenticationSupport {
  self: ServicesAware with JsonSupport =>

  get("/user/current") {
    scentry.userOption.getOrElse(401)
  }

  post("/user/authenticate") {
    if (!isAuthenticated) scentry.authenticate("UserPassword")

    authenticate.getOrElse(401)
  }

  get("/user/logout") {
    logOut()
    204
  }

  post("/user/register") {
    val parsedUser = parse(request.body).extract[JsonModels.UserRegister]
    services.userService.addNewUserAndReturnPin(parsedUser.name, parsedUser.email, parsedUser.password, uuid)
  }
}
