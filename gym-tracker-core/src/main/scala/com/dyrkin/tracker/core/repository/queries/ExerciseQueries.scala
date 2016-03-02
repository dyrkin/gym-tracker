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
      ((we, e), et) <- workouts2Exercises.
        join(exercises).on((we, e) => e.id === we.exerciseId).
      join(exerciseTypes).on((we_e, et) => we_e._2.exerciseTypeId === et.id)
    } yield (we, e, et)
    q.filter { case (we, e, et) => we.workoutId inSetBind ids }.map { case (we, e, et) => (we.workoutId, e.id, e.name, et.id, et.name) }
  }
}
