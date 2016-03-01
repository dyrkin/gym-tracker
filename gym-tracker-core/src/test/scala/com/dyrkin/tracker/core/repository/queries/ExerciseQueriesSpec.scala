package com.dyrkin.tracker.core.repository.queries

import com.dyrkin.tracker.core.driver.AgnosticDriver.api._
import com.dyrkin.tracker.core.repository.Tables._
import com.dyrkin.tracker.core.repository.TestData
import com.dyrkin.tracker.core.service.Services
import org.scalatest.{BeforeAndAfter, MustMatchers, WordSpec}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
  * @author eugene zadyra
  */
class ExerciseQueriesSpec extends WordSpec with MustMatchers with BeforeAndAfter {

  implicit class FutureResult[T](f: => Future[T]) {
    def exec: T = Await.result(f, Duration.Inf)
  }

  val services = new Services


  before {
    services.db.run(DBIO.seq(
      users.schema.create,
      exercises.schema.create,
      programs.schema.create,
      workouts.schema.create,
      workouts2Exercises.schema.create,
      workouts2Programs.schema.create,
      calendars.schema.create
    )).exec
    TestData.prepareData(services.db.source)
  }

//  after {
//    db.run(DBIO.seq(
//      workouts2Exercises.schema.drop,
//      workouts2Programs.schema.drop,
//      workouts.schema.drop,
//      programs.schema.drop,
//      users.schema.drop,
//      exercises.schema.drop,
//      calendars.schema.drop
//    )).exec
//  }

  "Database records" should {
    "be 1" in {
//      db.run(DBIO.seq(users +=(-1, "Hello")).transactionally).exec
      val res = services.programService.getActiveProgramByUserId(1)
      res.foreach(r => println(r))
    }
  }


}
