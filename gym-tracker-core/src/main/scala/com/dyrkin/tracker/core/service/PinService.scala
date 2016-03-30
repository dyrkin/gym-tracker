package com.dyrkin.tracker.core.service

import com.dyrkin.tracker.core.driver.AgnosticDriver.api._
import com.dyrkin.tracker.core.repository.queries._
import com.dyrkin.tracker.core.util._

import scala.concurrent.ExecutionContext.Implicits.global


/**
  * @author eugene zadyra
  */
class PinService(implicit val db: Database) {
  val queries = new Queries with PinQueries with UserQueries

  def removePin(pin: Int) = {
    val q = for {
      _ <- queries.removePin(pin)
    } yield ()
    db.run(q.transactionally).exec
  }
}
