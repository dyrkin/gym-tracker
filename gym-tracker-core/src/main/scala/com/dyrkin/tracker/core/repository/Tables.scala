package com.dyrkin.tracker.core.repository

import com.dyrkin.tracker.core.driver.AgnosticDriver.api._
import slick.lifted.Tag

/**
  * @author eugene zadyra
  */
object Tables {

  case class Pin(pin: Int, time: Long, userId: Long)

  class PinTable(tag: Tag) extends Table[Pin](tag, "PIN") {
    def pin = column[Int]("RAND_PIN", O.PrimaryKey)

    def time = column[Long]("TIME_CREATED")

    def userId = column[Long]("USER_ID")

    def user = foreignKey("P_USER_FK", userId, users)(_.id)

    def * = (pin, time, userId) <> (Pin.tupled, Pin.unapply)
  }

  lazy val pins = TableQuery[PinTable]

  case class User(id: Option[Long], name: String, email: String, hash: String, uuid:String)

  class UserTable(tag: Tag) extends Table[User](tag, "USER") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def name = column[String]("NAME")

    def email = column[String]("EMAIL")

    def hash = column[String]("HASH")

    def uuid = column[String]("UUID")

    def * = (id.?, name, email, hash, uuid) <> (User.tupled, User.unapply)
  }

  lazy val users = TableQuery[UserTable]

  case class Exercise(id: Option[Long], name: String, exerciseTypeId: Long)

  class ExerciseTable(tag: Tag) extends Table[Exercise](tag, "EXERCISE") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def name = column[String]("NAME")

    def exerciseTypeId = column[Long]("EXERCISE_TYPE_ID")

    def exerciseType = foreignKey("E_EXERCISE_TYPE_FK", exerciseTypeId, exerciseTypes)(_.id)

    def * = (id.?, name, exerciseTypeId) <> (Exercise.tupled, Exercise.unapply)
  }

  lazy val exercises = TableQuery[ExerciseTable]

  case class ExerciseType(id: Long, name: String)

  class ExerciseTypeTable(tag: Tag) extends Table[ExerciseType](tag, "EXERCISE_TYPE") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def name = column[String]("NAME")

    def * = (id, name) <> (ExerciseType.tupled, ExerciseType.unapply)
  }

  lazy val exerciseTypes = TableQuery[ExerciseTypeTable]

  case class Workout(id: Option[Long], name: String)

  class WorkoutTable(tag: Tag) extends Table[Workout](tag, "WORKOUT") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def name = column[String]("NAME")

    def * = (id.?, name) <> (Workout.tupled, Workout.unapply)
  }

  lazy val workouts = TableQuery[WorkoutTable]

  case class WorkoutExercise(workoutId: Long, exerciseId: Long)

  class WorkoutExerciseTable(tag: Tag) extends Table[WorkoutExercise](tag, "WORKOUT_EXERCISE") {
    def workoutId = column[Long]("WORKOUT_ID")

    def exerciseId = column[Long]("EXERCISE_ID")

    def workout = foreignKey("WE_WORKOUT_FK", workoutId, workouts)(_.id)

    def exercise = foreignKey("WE_EXERCISE_FK", exerciseId, exercises)(_.id)

    def * = (workoutId, exerciseId) <> (WorkoutExercise.tupled, WorkoutExercise.unapply)
  }

  lazy val workouts2Exercises = TableQuery[WorkoutExerciseTable]

  case class WorkoutProgram(workoutId: Long, programId: Long)

  class WorkoutProgramTable(tag: Tag) extends Table[WorkoutProgram](tag, "WORKOUT_PROGRAM") {
    def workoutId = column[Long]("WORKOUT_ID")

    def programId = column[Long]("PROGRAM_ID")

    def workout = foreignKey("WP_WORKOUT_FK", workoutId, workouts)(_.id)

    def program = foreignKey("WP_PROGRAM_FK", programId, programs)(_.id)

    def * = (workoutId, programId) <> (WorkoutProgram.tupled, WorkoutProgram.unapply)
  }

  lazy val workouts2Programs = TableQuery[WorkoutProgramTable]

  case class Calendar(id: Option[Long], workoutId: Long)

  class CalendarTable(tag: Tag) extends Table[Calendar](tag, "CALENDAR") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def workoutId = column[Long]("WORKOUT_ID")

    def workout = foreignKey("C_WORKOUT_FK", workoutId, workouts)(_.id)

    def * = (id.?, workoutId) <> (Calendar.tupled, Calendar.unapply)
  }

  lazy val calendars = TableQuery[CalendarTable]

  case class DayOfWeek(id: Option[Long], day: Int, calendarId: Long)

  class DayOfWeekTable(tag: Tag) extends Table[DayOfWeek](tag, "DAY_OF_WEEK") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def day = column[Int]("DAY")

    def calendarId = column[Long]("CALENDAR_ID")

    def calendar = foreignKey("D_CALENDAR_FK", calendarId, calendars)(_.id)

    def * = (id.?, day, calendarId) <> (DayOfWeek.tupled, DayOfWeek.unapply)
  }

  lazy val daysOfWeek = TableQuery[DayOfWeekTable]

  case class Program(id: Option[Long], name: String, isActive: Int, userId: Long)

  class ProgramTable(tag: Tag) extends Table[Program](tag, "PROGRAM") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def name = column[String]("NAME")

    def isActive = column[Int]("ACTIVE")

    def userId = column[Long]("USER_ID")

    def user = foreignKey("P_USER_FK", userId, users)(_.id)

    def * = (id.?, name, isActive, userId) <> (Program.tupled, Program.unapply)
  }

  lazy val programs = TableQuery[ProgramTable]
}
