package com.dyrkin.tracker.core.vo

/**
  * @author eugene zadyra
  */

case class ExerciseType(id: Long, name: String)
case class Exercise(id: Long, name: String, `type`: ExerciseType)
case class Day(day:Int)
case class Workout(id: Long, name: String, exercises: Seq[Exercise], days: Seq[Day])
case class Program(id: Long, name: String, workouts:Seq[Workout])
case class Value(value: Int)

object WatchUserDetails {
  def apply(user: (Long, String, String, String)) = WatchUserDetails(user._1, user._2, user._3, user._4)
}
case class WatchUserDetails(id: Long, name: String, email: String, uuid: String)


