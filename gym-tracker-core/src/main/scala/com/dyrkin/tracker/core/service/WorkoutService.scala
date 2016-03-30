package com.dyrkin.tracker.core.service

import com.dyrkin.tracker.core.driver.AgnosticDriver.api._
import com.dyrkin.tracker.core.repository.Tables.Workout
import com.dyrkin.tracker.core.repository.queries.{Queries, WorkoutQueries}
import com.dyrkin.tracker.core.util.FutureResult

/**
  * Created by ezadyra on 3/30/2016.
  */
class WorkoutService(implicit val db: Database) {
  val queries = new Queries with WorkoutQueries

  def saveWorkout(workout: Workout): Workout = {
    val workoutId = db.run(queries.saveWorkout(workout)).exec
    // in case workout updated query returns None this is means that we need
    // to return original entity. If query returns Some(workoutId) than we need
    // to extract this workout and return as a result
    workoutId.flatMap(id => getWorkoutById(id)).getOrElse(workout)
  }

  def getWorkoutById(id: Long): Option[Workout] = {
    db.run(queries.getWorkoutById(id).result).exec.headOption
  }
}
