package com.dyrkin.tracker.core.repository.queries

import com.dyrkin.tracker.core.repository.DatabaseSupport
import com.dyrkin.tracker.core.repository.Tables._
import com.dyrkin.tracker.core.driver.AgnosticDriver.api._

/**
  * @author eugene zadyra
  */
trait ExerciseQueries {
  self: DatabaseSupport =>

  def getExercisesByWorkoutIds(ids: Seq[Long]) = {
    val q = for {
      (we, e) <- workouts2Exercises.
        join(exercises).on((we, e) => e.id === we.exerciseId)
    } yield (we, e)
    q.filter { case (we, e) => we.workoutId inSetBind ids }.map { case (we, e) => (we.workoutId, e.id, e.name) }
  }
}
