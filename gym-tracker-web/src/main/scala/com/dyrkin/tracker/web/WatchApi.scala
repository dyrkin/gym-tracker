package com.dyrkin.tracker.web

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.ScalatraServlet
import org.scalatra.json._

/**
  * @author eugene zadyra
  */
trait WatchApi extends JacksonJsonSupport {
  self: ServicesAware with ScalatraServlet =>

  override protected implicit lazy val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/") {
    services.programService.getActiveProgramByUserId(1)
  }
}
