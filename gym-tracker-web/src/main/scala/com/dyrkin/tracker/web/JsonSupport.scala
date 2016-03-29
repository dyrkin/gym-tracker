package com.dyrkin.tracker.web

import com.dyrkin.tracker.core.util.Log
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport

/**
  * @author eugene zadyra
  */
trait JsonSupport extends JacksonJsonSupport with Log{
  override protected implicit lazy val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
    debug(s"Request to Path: /json${request.getPathInfo}")
  }
}
