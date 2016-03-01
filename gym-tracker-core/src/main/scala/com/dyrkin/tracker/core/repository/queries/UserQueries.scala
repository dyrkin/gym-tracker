package com.dyrkin.tracker.core.repository.queries

import com.dyrkin.tracker.core.repository.DatabaseSupport
import com.dyrkin.tracker.core.repository.Tables._
import com.dyrkin.tracker.core.driver.AgnosticDriver.api._

/**
  * @author eugene zadyra
  */
trait UserQueries {
  self: DatabaseSupport =>

  def addUser(name: String, email: String, hash: String, apiId: Int, pin: Int) = {
    DBIO.seq(users +=(-1, name, email, hash))
  }
}
