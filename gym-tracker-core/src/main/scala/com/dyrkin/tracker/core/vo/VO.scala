package com.dyrkin.tracker.core.vo

/**
  * @author eugene zadyra
  */

case class Exercise(id: Long, name: String)
case class Workout(id: Long, name: String, exercises: Seq[Exercise])
case class Program(id: Long, name: String, workouts:Seq[Workout])
case class Value(value: Int)


