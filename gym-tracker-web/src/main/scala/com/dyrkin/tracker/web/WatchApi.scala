package com.dyrkin.tracker.web

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.ScalatraServlet
import org.scalatra.json._

/**
  * @author eugene zadyra
  */
trait WatchApi extends JacksonJsonSupport {
  self: ServicesAware with JsonSupport =>

  get("/api/program") {
    services.programService.getActiveProgramByUserId(1)
  }

  get("/api/validate") {
    val pin = params("pin")

    services.userService.getUserDetailsByPin(pin.toInt)
  }

  get("/api/remove_pin") {
    val pin = params("pin")

    services.pinService.removePin(pin.toInt)
    response.setStatus(200)
  }
}
