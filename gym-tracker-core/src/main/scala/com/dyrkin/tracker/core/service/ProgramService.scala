package com.dyrkin.tracker.core.service

import com.dyrkin.tracker.core.driver.AgnosticDriver.api._
import com.dyrkin.tracker.core.repository.queries.{ExerciseQueries, WorkoutQueries, ProgramQueries, Queries}
import com.dyrkin.tracker.core.vo.{Workout, Exercise, Program}
import scala.concurrent.ExecutionContext.Implicits.global
import com.dyrkin.tracker.core.util._

import scala.concurrent.Future

/**
  * @author eugene zadyra
  */
class ProgramService(implicit val db: Database) {
  val queries = new Queries with ProgramQueries with WorkoutQueries with ExerciseQueries

  private def activeProgrammByUserId(id: Long) = {
    db.run(queries.getActiveProgramByUserIdCompiled(id).result)
  }

  private def workoutsByProgramId(id: Long) = {
    db.run(queries.getWorkoutsByProgramIdCompiled(id).result)
  }

  private def exersisesByWorkoutIds(ids: Seq[Long]) = {
    db.run(queries.getExercisesByWorkoutIds(ids).result)
  }

  def getActiveProgramByUserId(id: Long) = {
    val programsFuture = activeProgrammByUserId(id).map {
      programs => programs.map { case (pId, pName) => workoutsByProgramId(pId).flatMap {
        workouts => exersisesByWorkoutIds(workouts.map(_._1)).flatMap {
          exersises =>
            Future {
              Program(pId, pName, workouts.map { case (wId, wName) => Workout(wId, wName, exersises.filter(_._1 == wId).map { case (_, eId, eName) => Exercise(eId, eName) }) })
            }
        }
      }
      }
    }
    programsFuture.exec.map(_.exec)
  }
}
