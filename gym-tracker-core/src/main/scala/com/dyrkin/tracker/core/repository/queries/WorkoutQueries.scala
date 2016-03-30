package com.dyrkin.tracker.core.repository.queries

import com.dyrkin.tracker.core.driver.AgnosticDriver.api._
import com.dyrkin.tracker.core.repository.DatabaseSupport
import com.dyrkin.tracker.core.repository.Tables._


/**
  * @author eugene zadyra
  */
trait WorkoutQueries extends BaseQueries {
  self: DatabaseSupport =>

  val getWorkoutsByProgramIdCompiled = Compiled(getWorkoutsByProgramId _)

  private def getWorkoutsByProgramId(id: Rep[Long]) = {
    val q = for {
      (w, wp) <- workouts.
        join(workouts2Programs).on((w, wp) => w.id === wp.workoutId)
    } yield (w, wp)
    q.filter { case (w, wp) => wp.programId === id }.map { case (w, wp) => (w.id, w.name) }
  }

  def saveWorkout(workout: Workout)= {
    insertOrUpdate(workouts, workout)
  }

  def getWorkoutById(id: Long)= {
    workouts.filter(_.id === id)
  }
}
