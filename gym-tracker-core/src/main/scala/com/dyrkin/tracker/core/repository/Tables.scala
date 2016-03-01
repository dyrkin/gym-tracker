package com.dyrkin.tracker.core.repository

import com.dyrkin.tracker.core.driver.AgnosticDriver.api._
import slick.lifted.Tag

/**
  * @author eugene zadyra
  */
object Tables {

  class Pin(tag: Tag) extends Table[(Long, Long, Long)](tag, "PIN") {
    def pin = column[Long]("RAND_PIN", O.PrimaryKey)

    def time = column[Long]("TIME_CREATED")

    def userId = column[Long]("USER_ID")

    def user = foreignKey("P_USER_FK", userId, users)(_.id)

    def * = (pin, time, userId)
  }

  lazy val pins = TableQuery[Pin]

  class User(tag: Tag) extends Table[(Long, String, String, String)](tag, "USER") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def name = column[String]("NAME")

    def email = column[String]("EMAIL")

    def hash = column[String]("HASH")

    def * = (id, name, email, hash)
  }

  lazy val users = TableQuery[User]

  class Exercise(tag: Tag) extends Table[(Long, String, Long)](tag, "EXERCISE") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def name = column[String]("NAME")

    def userId = column[Long]("USER_ID")

    def user = foreignKey("E_USER_FK", userId, users)(_.id)

    def * = (id, name, userId)
  }

  lazy val exercises = TableQuery[Exercise]

  class Workout(tag: Tag) extends Table[(Long, String)](tag, "WORKOUT") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def name = column[String]("NAME")

    def * = (id, name)
  }

  lazy val workouts = TableQuery[Workout]

  class WorkoutExercise(tag: Tag) extends Table[(Long, Long)](tag, "WORKOUT_EXERCISE") {
    def workoutId = column[Long]("WORKOUT_ID")

    def exerciseId = column[Long]("EXERCISE_ID")

    def workout = foreignKey("WE_WORKOUT_FK", workoutId, workouts)(_.id)

    def exercise = foreignKey("WE_EXERCISE_FK", exerciseId, exercises)(_.id)

    def * = (workoutId, exerciseId)
  }

  lazy val workouts2Exercises = TableQuery[WorkoutExercise]

  class WorkoutProgram(tag: Tag) extends Table[(Long, Long)](tag, "WORKOUT_PROGRAM") {
    def workoutId = column[Long]("WORKOUT_ID")

    def programId = column[Long]("PROGRAM_ID")

    def workout = foreignKey("WP_WORKOUT_FK", workoutId, workouts)(_.id)

    def program = foreignKey("WP_PROGRAM_FK", programId, programs)(_.id)

    def * = (workoutId, programId)
  }

  lazy val workouts2Programs = TableQuery[WorkoutProgram]

  class Calendar(tag: Tag) extends Table[(Long, String)](tag, "CALENDAR") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def name = column[String]("NAME")

    def * = (id, name)
  }

  lazy val calendars = TableQuery[Calendar]

  class Program(tag: Tag) extends Table[(Long, String, Int, Long)](tag, "PROGRAM") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def name = column[String]("NAME")

    def isActive = column[Int]("ACTIVE")

    def userId = column[Long]("USER_ID")

    def user = foreignKey("P_USER_FK", userId, users)(_.id)

    def * = (id, name, isActive, userId)
  }

  lazy val programs = TableQuery[Program]
}
