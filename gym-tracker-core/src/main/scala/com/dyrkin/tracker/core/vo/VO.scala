package com.dyrkin.tracker.core.vo

/**
  * @author eugene zadyra
  */

case class ExerciseType(id: Long, name: String)
case class Exercise(id: Long, name: String, `type`: ExerciseType)
case class Day(dayOfWeek:Int)
case class Workout(id: Long, name: String, exercises: Seq[Exercise], days: Seq[Day])
case class Program(id: Long, name: String, workouts:Seq[Workout])
case class Value(value: Int)
case class WatchUserDetails(name: String, email: String, uuid: String)


