package com.dyrkin.tracker.core.repository.queries

import com.dyrkin.tracker.core.repository.DatabaseSupport
import com.dyrkin.tracker.core.repository.Tables._
import com.dyrkin.tracker.core.driver.AgnosticDriver.api._


/**
  * @author eugene zadyra
  */
trait ProgramQueries {
  self: DatabaseSupport =>

  //  def getActiveProgramByUserId(id: Long) = {
  //     val q = for {
  //       ((((p, wp), w), we), e) <- programs.
  //         joinLeft(workouts2Programs).on((p, wp) => p.id === wp.programId).
  //         joinLeft(workouts).on((p_wp, w) => p_wp._2.map(_.workoutId) === w.id).
  //         joinLeft(workouts2Exercises).on((p_wp_w, we) => p_wp_w._2.map(_.id) === we.workoutId).
  //         joinLeft(exercises).on((p_wp_w_we, e) => p_wp_w_we._2.map(_.exerciseId) === e.id)
  //
  //     } yield (p, w, e)
  //    q.filter(v => v._1.isActive === 1 && v._1.userId === id)
  //  }

  def getActiveProgramByUserId(id: Long) = {
    val q = for {
      p <- programs
    } yield p
    q.filter(p => p.isActive === 1 && p.userId === id).map(p => (p.id, p.name))
  }
}
