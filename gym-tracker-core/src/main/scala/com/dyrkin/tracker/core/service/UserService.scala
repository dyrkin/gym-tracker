package com.dyrkin.tracker.core.service

import com.dyrkin.tracker.core.driver.AgnosticDriver.api._
import com.dyrkin.tracker.core.repository.Tables._
import com.dyrkin.tracker.core.repository.queries._
import com.dyrkin.tracker.core.util._

import scala.concurrent.ExecutionContext.Implicits.global


/**
  * @author eugene zadyra
  */
class UserService(implicit val db: Database) {
  val queries = new Queries with UserQueries with PinQueries

  def addNewUser(name: String, email: String, pHash: String) = {
    val q = for {
      userId <- users returning users.map(_.id) +=(-1, name, email, pHash)
      pin <- queries.getUniquePin.head
      _ <- queries.savePin(pin, current.getTime, userId)
    } yield pin
    db.run(q.transactionally).exec
  }

}
