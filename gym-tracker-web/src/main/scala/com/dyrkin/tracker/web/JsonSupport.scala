package com.dyrkin.tracker.web

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport

/**
  * @author eugene zadyra
  */
trait JsonSupport extends JacksonJsonSupport {
  override protected implicit lazy val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }
}
