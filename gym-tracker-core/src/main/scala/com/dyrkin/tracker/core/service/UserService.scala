package com.dyrkin.tracker.core.service

import com.dyrkin.tracker.core.driver.AgnosticDriver.api._
import com.dyrkin.tracker.core.repository.queries._
import com.dyrkin.tracker.core.util._
import com.dyrkin.tracker.core.vo.{WatchUserDetails, Value}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationLong


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
      userDetails <- queries.userDetailsById(userId._1).result.head
    } yield (userId, userDetails)
    val (userId, details) = db.run(q.transactionally).exec

    validateTime(userId._2)
    WatchUserDetails(details)
  }

  def validateTime(pinCreated: Long) = {
    val timeElapsedSincePinCreation = current.getTime - pinCreated

    if (C.pinValidDuration.toMillis < timeElapsedSincePinCreation) {
      sys.error(s"Pin is invalid. Please recreate it and use in period: ${C.pinValidDuration.toString()}")
    }
  }

  def getUserDetailsById(id: Long): Option[WatchUserDetails] = {
    val users = db.run(queries.userDetailsById(id).result).exec
    users.headOption.map(u => WatchUserDetails(u))
  }

  def userByEmailAndPassword(email: String, password: String): Option[WatchUserDetails] = {
    val users = db.run(queries.userByEmailAndPassword(email, password).result).exec
    users.headOption.map(u => WatchUserDetails(u))
  }
}
