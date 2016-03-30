package com.dyrkin.tracker.core.repository.queries

import com.dyrkin.tracker.core.repository.DatabaseSupport
import com.dyrkin.tracker.core.repository.Tables._
import com.dyrkin.tracker.core.driver.AgnosticDriver.api._

/**
  * @author eugene zadyra
  */
trait UserQueries {
  self: DatabaseSupport =>

  def addUser(name: String, email: String, hash: String, uuid: String) = {
    users returning users.map(_.id) += User(None, name, email, hash, uuid)
  }

  def userIdByEmail(email: String) = {
    users.filter(_.email === email)
  }

  def userDetailsById(id: Long) = {
    users.filter(_.id === id)
  }

  def userIdByNameAndUUID(name: String, uuid: String) = {
    users.filter(u => u.name === name && u.uuid === uuid).map(_.id)
  }

  def userByEmailAndPassword(email: String, password: String) = {
    users.filter(u => u.email === email && u.hash === password)
  }

  def userByEmail(email: String) = {
    users.filter(u => u.email === email)
  }
}
