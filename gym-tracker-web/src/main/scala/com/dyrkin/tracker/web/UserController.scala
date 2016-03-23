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
   services.userService.getUserDetailsById(1L) // TODO: Change it to Get current authorized user from App CONTEXT

    // or 401
  }

  post("/user/authenticate") {
    val user = parse(request.body).extract[JsonModels.UserLogin]
    if (user.email == "admin" && user.password == "admin") println(user) else  401
  }

  get("/user/register") {
    val userName = params("name")
    val email = params("email")
    val pHash = params("phash")

    services.userService.addNewUserAndReturnPin(userName, email, pHash, uuid)
  }
}
