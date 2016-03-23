package com.dyrkin.tracker.web

import org.scalatra.scalate.ScalateSupport

/**
  * @author ihor zadyra
  */
trait IndexController extends ScalateSupport {

  get("/") {
    contentType = "text/html"
    layoutTemplate("/WEB-INF/views/index.ssp")
  }
}
