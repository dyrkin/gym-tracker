package com.dyrkin.tracker.core.service

import com.dyrkin.tracker.core.driver.AgnosticDriver.api._
import com.dyrkin.tracker.core.repository.queries._
import com.dyrkin.tracker.core.vo._
import scala.concurrent.ExecutionContext.Implicits.global
import com.dyrkin.tracker.core.util._

import scala.concurrent.Future

/**
  * @author eugene zadyra
  */
class ProgramService(implicit val db: Database) {
  val queries = new Queries with ProgramQueries with WorkoutQueries with ExerciseQueries with CalendarQueries

  private def activeProgrammByUserId(id: Long) = {
    db.run(queries.getActiveProgramByUserIdCompiled(id).result)
  }

  private def workoutsByProgramId(id: Long) = {
    db.run(queries.getWorkoutsByProgramIdCompiled(id).result)
  }

  private def exersisesByWorkoutIds(ids: Seq[Long]) = {
    db.run(queries.getExercisesByWorkoutIds(ids).result)
  }

  private def daysByWorkoutIds(ids: Seq[Long]) = {
    db.run(queries.daysByWorkoutIds(ids).result)
  }

  def getActiveProgramByUserId(id: Long) = {
    val programsFuture = activeProgrammByUserId(id).map {
      programs => programs.map { case (pId, pName) => workoutsByProgramId(pId).flatMap {
        workouts => Future.sequence(List(exersisesByWorkoutIds(workouts.map(_._1)), daysByWorkoutIds(workouts.map(_._1)))).flatMap {
          case List(exercises: Seq[(Long, Long, String, Long, String)], daysOfWeek: Seq[(Long, Int)]) =>
            Future {
              Program(pId, pName, workouts.map { case (wId, wName) =>
                Workout(wId, wName,
                  exercises.filter(_._1 == wId).map { case (_, eId, eName, etId, etName) => Exercise(eId, eName, ExerciseType(etId, etName)) },
                  daysOfWeek.filter(_._1 == wId).map { case (_, day) => Day(day) })
              })
            }
        }
      }
      }
    }
    programsFuture.exec.map(_.exec)
  }
}
