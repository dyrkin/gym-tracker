package com.dyrkin.tracker.core.service

import com.dyrkin.tracker.core.repository.DatabaseTestBase
import org.scalatest.{BeforeAndAfter, FunSuiteLike, Matchers}
import org.scalatest.mock.MockitoSugar
import com.dyrkin.tracker.core.repository.Tables._


/**
  * Created by ezadyra on 3/30/2016.
  */
class WorkoutServiceSpec extends FunSuiteLike with MockitoSugar with BeforeAndAfter with Matchers with DatabaseTestBase {

  val services = new Services

  def db = services.db

  test("Test create new workout") {
    val workout = Workout(None, "MyNewWorkout")
    services.workoutService.saveWorkout(workout)
    val savedWorkout = services.workoutService.getWorkoutById(1L)

    savedWorkout shouldBe Some(Workout(Some(1), "MyNewWorkout"))
  }

  test("Test update workout") {
    val workout = Workout(None, "MyNewWorkout")
    val savedWorkout = services.workoutService.saveWorkout(workout)
    services.workoutService.saveWorkout(savedWorkout.copy(name = "MyUpdatedWorkout"))
    val updatedWorkout = services.workoutService.getWorkoutById(1L)

    updatedWorkout shouldBe Some(Workout(Some(1), "MyUpdatedWorkout"))
  }
}
