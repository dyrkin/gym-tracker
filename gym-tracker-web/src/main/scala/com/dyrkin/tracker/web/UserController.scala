package com.dyrkin.tracker.web

import com.dyrkin.tracker.core.util._
import com.dyrkin.tracker.web.model.JsonModels
import org.scalatra.json.JacksonJsonSupport

/**
  * @author eugene zadyra
  */
trait UserController extends JacksonJsonSupport {
  self: ServicesAware with JsonSupport =>

  get("/user/current") {
    val currentUser = request.getSession.getAttribute("currentUser")
    if (currentUser == null) 401 else currentUser
  }

  post("/user/authenticate") {
    val parsedUser = parse(request.body).extract[JsonModels.UserLogin]
    val user = services.userService.userByEmailAndPassword(parsedUser.email, parsedUser.password)
    if (user == null)
      401
    else {
      request.getSession.setAttribute("currentUser", user)
      user
    }
  }

  get("/user/logout") {
    request.getSession.setAttribute("currentUser", null)
    401
  }

  post("/user/register") {
    val parsedUser = parse(request.body).extract[JsonModels.UserRegister]
    services.userService.addNewUserAndReturnPin(parsedUser.name, parsedUser.email, parsedUser.password, uuid)

    val userFromDb = services.userService.userByEmailAndPassword(parsedUser.email, parsedUser.password)
    request.getSession.setAttribute("currentUser", userFromDb)
  }
}
