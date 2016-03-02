package com.dyrkin.tracker.core.repository.queries

import com.dyrkin.tracker.core.driver.AgnosticDriver.api._
import com.dyrkin.tracker.core.repository.DatabaseSupport
import com.dyrkin.tracker.core.repository.Tables._

/**
  * @author eugene zadyra
  */
trait PinQueries {
  self: DatabaseSupport =>

  def uniquePin = {
    sql"""
       SELECT random_num
       FROM (
         SELECT FLOOR(RAND() * 9999) AS random_num
         FROM PIN
         UNION
         SELECT FLOOR(RAND() * 9999) AS random_num
       ) AS numbers_mst_plus_1
       WHERE "random_num" NOT IN (SELECT RAND_PIN FROM PIN)
       LIMIT 1
       """.as[Int]
  }

  def savePin(pin: Int, time: Long, userId: Long) = {
    pins += (pin, time, userId)
  }

  def userIdAndTimeByPin(pin: Int) = {
    pins.filter(_.pin === pin).map{p => (p.userId, p.time)}
  }

  def removePin(pin: Int) = {
    pins.filter(_.pin === pin).delete
  }

}
