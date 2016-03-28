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
    //  services.userService.getUserDetailsById(1L) // TODO: Change it to Get current authorized user from App CONTEXT
    val currentUser = request.getSession.getAttribute("currentUser")
    if (currentUser == null) 401 else println(currentUser)
  }

  post("/user/authenticate") {
    val parsedUser = parse(request.body).extract[JsonModels.UserLogin]
    val user = services.userService.userByEmailAndPassword(parsedUser.email, parsedUser.password)

    println(user)
    if (user != None) {
      request.getSession.setAttribute("currentUser", user)
    user
  } else
      401
  }

  get("/user/logout") {

    request.getSession.setAttribute("currentUser", None)

    redirect("/json/user/current")
  }

  post("/user/register") {
    val user = parse(request.body).extract[JsonModels.UserRegister]
    println(user.email)
    println(user.name)
    println(user.password)
    services.userService.addNewUserAndReturnPin(user.email, user.name, user.password, uuid)
  }
}
