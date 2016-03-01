package com.dyrkin.tracker.web

import com.dyrkin.tracker.core.service.ServicesT

/**
  * @author eugene zadyra
  */
trait ServicesAware {
  def services: ServicesT
}
