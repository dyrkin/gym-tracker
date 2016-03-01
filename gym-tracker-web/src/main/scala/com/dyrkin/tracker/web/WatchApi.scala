package com.dyrkin.tracker.web

import org.scalatra.scalate.ScalateSupport

/**
  * @author eugene zadyra
  */
trait WatchApi extends ScalateSupport {
  self: ServicesAware =>
  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say
        <a href="hello-scalate">hello to Scalate</a>
        .
      </body>
    </html>
  }
}
