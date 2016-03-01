package com.dyrkin.tracker.web

import com.dyrkin.tracker.core.service.ServicesT
import org.scalatra.ScalatraServlet

/**
  * @author eugene zadyra
  */
class Routes(val services: ServicesT) extends ScalatraServlet with ServicesAware with WatchApi {

}
