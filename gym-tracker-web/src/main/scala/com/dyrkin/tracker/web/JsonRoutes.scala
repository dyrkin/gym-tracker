package com.dyrkin.tracker.web

import com.dyrkin.tracker.core.service.ServicesT
import org.scalatra.ScalatraServlet
import org.scalatra.scalate.ScalateSupport

/**
  * @author eugene zadyra
  */
class JsonRoutes(val services: ServicesT) extends ScalatraServlet with ScalateSupport with JsonSupport with ServicesAware
  with WatchApi with UserController with IndexController {
}
