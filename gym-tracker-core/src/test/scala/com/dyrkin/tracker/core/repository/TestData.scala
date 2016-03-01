package com.dyrkin.tracker.core.repository

import java.sql.Connection

import com.ninja_squad.dbsetup.generator.ValueGenerators
import com.ninja_squad.dbsetup.{DbSetupTracker, DbSetup}
import com.ninja_squad.dbsetup.Operations._
import com.ninja_squad.dbsetup.destination.{Destination}
import slick.jdbc.JdbcDataSource

/**
  * @author eugene zadyra
  */
object TestData {

  class ScalaDataSourceDestination(dataSouse: JdbcDataSource) extends Destination {
    override def getConnection: Connection = dataSouse.createConnection()
  }

  implicit class Any2Object(any: Any) {
    def o = any.asInstanceOf[Object]
  }

  val dbSetupTracker = new DbSetupTracker()

  val insertUsers = insertInto("USER")
    .withGeneratedValue("ID", ValueGenerators.sequence().startingAt(1L))
    .columns("NAME")
    .values("TEST_USER1")
    .values("TEST_USER2")
    .build()

  val insertExercises = insertInto("EXERCISE")
    .withGeneratedValue("ID", ValueGenerators.sequence().startingAt(1L))
    .columns("NAME", "USER_ID")
    .values("EXERCISE1", 1L.o)
    .values("EXERCISE2", 1L.o)
    .values("EXERCISE3", 1L.o)
    .values("EXERCISE4", 1L.o)
    .values("EXERCISE5", 1L.o)
    .build()

  val insertPrograms = insertInto("PROGRAM")
    .withGeneratedValue("ID", ValueGenerators.sequence().startingAt(1L))
    .columns("NAME", "ACTIVE", "USER_ID")
    .values("PROGRAM1", 1.o, 1L.o)
    .values("PROGRAM2", 0.o, 1L.o)
    .values("PROGRAM3", 1.o, 2L.o)
    .build()

  val insertWorkouts = insertInto("WORKOUT")
    .withGeneratedValue("ID", ValueGenerators.sequence().startingAt(1L))
    .columns("NAME")
    .values("WORKOUT1")
    .values("WORKOUT2")
    .values("WORKOUT3")
    .values("WORKOUT4")
    .build()

  val insertWorkouts2Exercises = insertInto("WORKOUT_EXERCISE")
    .columns("WORKOUT_ID", "EXERCISE_ID")
    .values(1L.o, 1L.o)
    .values(1L.o, 2L.o)
    .values(1L.o, 3L.o)
    .values(2L.o, 4L.o)
    .values(2L.o, 5L.o)
    .build()

  val insertWorkouts2Programs = insertInto("WORKOUT_PROGRAM")
    .columns("WORKOUT_ID", "PROGRAM_ID")
    .values(1L.o, 1L.o)
    .values(2L.o, 1L.o)
    .values(3L.o, 1L.o)
    .values(4L.o, 2L.o)
    .build()

  def prepareData(dataSouse: JdbcDataSource): Unit = {
    val all = sequenceOf(insertUsers, insertExercises, insertPrograms,
      insertWorkouts, insertWorkouts2Exercises, insertWorkouts2Programs)
    val dbSetup = new DbSetup(new ScalaDataSourceDestination(dataSouse), all)
    dbSetupTracker.launchIfNecessary(dbSetup)
  }
}
