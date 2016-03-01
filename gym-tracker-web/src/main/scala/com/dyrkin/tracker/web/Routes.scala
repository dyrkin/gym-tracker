package com.dyrkin.tracker.web

import com.dyrkin.tracker.core.service.ServicesT
import org.scalatra.ScalatraServlet
import org.scalatra.scalate.ScalateSupport

/**
  * @author eugene zadyra
  */
class Routes(val services: ServicesT) extends ScalatraServlet with ScalateSupport with ServicesAware with WatchApi {

}
