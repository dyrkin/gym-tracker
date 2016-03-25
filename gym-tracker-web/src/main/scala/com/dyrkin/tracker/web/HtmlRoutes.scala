package com.dyrkin.tracker.web

import org.scalatra.ScalatraServlet
import org.scalatra.scalate.ScalateSupport

/**
  * @author ihor zadyra
  */
class HtmlRoutes extends ScalatraServlet with ScalateSupport {

  get("/") {
    contentType = "text/html"
    layoutTemplate("/WEB-INF/views/index.ssp")
  }
}
