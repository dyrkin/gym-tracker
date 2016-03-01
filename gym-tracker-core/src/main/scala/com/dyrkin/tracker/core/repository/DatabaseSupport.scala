package com.dyrkin.tracker.core.repository

import com.dyrkin.tracker.core.driver.AgnosticDriver.api._

/**
  * @author eugene zadyra
  */
trait DatabaseSupport {
  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

  def db: Database
}
