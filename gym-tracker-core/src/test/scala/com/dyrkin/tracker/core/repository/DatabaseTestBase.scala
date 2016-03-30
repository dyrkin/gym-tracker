package com.dyrkin.tracker.core.repository

import com.dyrkin.tracker.core.driver.AgnosticDriver.api._
import com.dyrkin.tracker.core.util.FutureResult
import org.scalatest.{BeforeAndAfter, FunSuiteLike}
import Tables._

/**
  * Created by ezadyra on 3/30/2016.
  */
trait DatabaseTestBase extends FunSuiteLike with BeforeAndAfter {

  def db: Database

  before {
    db.run(DBIO.seq((
      users.schema ++
        pins.schema ++
        exerciseTypes.schema ++
        exercises.schema ++
        programs.schema ++
        workouts.schema ++
        workouts2Exercises.schema ++
        workouts2Programs.schema ++
        calendars.schema ++
        daysOfWeek.schema
      ).create)).exec
  }

  after {
    db.run(DBIO.seq((
      users.schema ++
        pins.schema ++
        exerciseTypes.schema ++
        exercises.schema ++
        programs.schema ++
        workouts.schema ++
        workouts2Exercises.schema ++
        workouts2Programs.schema ++
        calendars.schema ++
        daysOfWeek.schema
      ).drop)).exec
  }
}
