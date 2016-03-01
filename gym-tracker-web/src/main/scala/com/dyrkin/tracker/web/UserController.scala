package com.dyrkin.tracker.web

import com.dyrkin.tracker.core.vo.Value
import org.scalatra.json.JacksonJsonSupport

/**
  * @author eugene zadyra
  */
trait UserController extends JacksonJsonSupport {
  self: ServicesAware with JsonSupport =>

  get("/user/add") {
    val userName = params("name")
    val email = params("email")
    val pHash = params("phash")
    val pin = services.userService.addNewUser(userName, email, pHash)
    Value(pin)
  }
}
