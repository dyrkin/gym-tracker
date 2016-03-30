package com.dyrkin.tracker.core.service

import com.dyrkin.tracker.core.driver.AgnosticDriver.api._
import com.dyrkin.tracker.core.repository.Tables.User
import com.dyrkin.tracker.core.repository.queries._
import com.dyrkin.tracker.core.util._
import com.dyrkin.tracker.core.vo.Value

import scala.concurrent.ExecutionContext.Implicits.global


/**
  * @author eugene zadyra
  */
class UserService(implicit val db: Database) {
  val queries = new Queries with UserQueries with PinQueries

  def addNewUserAndReturnPin(name: String, email: String, hash: String, uuid: String) = {
    val q = for {
      userId <- queries.addUser(name, email, hash, uuid)
      pin <- queries.uniquePin.head
      _ <- queries.savePin(pin, current.getTime, userId)
    } yield pin
    Value(db.run(q.transactionally).exec)
  }

  def getUserDetailsByPin(pin: Int) = {
    val q = for {
      userId <- queries.userIdAndTimeByPin(pin).result.head
      userDetails <- queries.userDetailsById(userId._1).result.headOption
    } yield (userId, userDetails)
    val (userId, user) = db.run(q.transactionally).exec

    validateTime(userId._2)
    user
  }

  def validateTime(pinCreated: Long) = {
    val timeElapsedSincePinCreation = current.getTime - pinCreated

    if (C.pinValidDuration.toMillis < timeElapsedSincePinCreation) {
      sys.error(s"Pin is invalid. Please recreate it and use in period: ${C.pinValidDuration.toString()}")
    }
  }

  def getUserDetailsById(id: Long): Option[User] = {
    val users = db.run(queries.userDetailsById(id).result).exec
    users.headOption
  }

  def userByEmailAndPassword(email: String, password: String): Option[User] = {
    val users = db.run(queries.userByEmailAndPassword(email, password).result).exec
    users.headOption
  }

  def userByEmail(email: String): Option[User] = {
    val users = db.run(queries.userByEmail(email).result).exec
    users.headOption
  }
}
