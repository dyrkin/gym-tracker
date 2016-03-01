package com.dyrkin.tracker.core.service

import com.dyrkin.tracker.core.driver.AgnosticDriver.api._

/**
  * @author eugene zadyra
  */

trait ServicesT {
  implicit def db: Database

  def programService: ProgramService

  def userService: UserService
}

class Services extends ServicesT {
  implicit val db = Database.forConfig("db")

  val programService = new ProgramService

  val userService = new UserService
}
