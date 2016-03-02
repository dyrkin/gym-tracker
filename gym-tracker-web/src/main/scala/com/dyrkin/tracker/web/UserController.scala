package com.dyrkin.tracker.web

import com.dyrkin.tracker.core.vo.Value
import org.scalatra.json.JacksonJsonSupport
import com.dyrkin.tracker.core.util._

/**
  * @author eugene zadyra
  */
trait UserController extends JacksonJsonSupport {
  self: ServicesAware with JsonSupport =>

  get("/user/register") {
    val userName = params("name")
    val email = params("email")
    val pHash = params("phash")

    services.userService.addNewUserAndReturnPin(userName, email, pHash, uuid)

  }
}
