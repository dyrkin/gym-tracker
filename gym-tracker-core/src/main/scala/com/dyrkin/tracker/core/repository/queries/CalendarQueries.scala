package com.dyrkin.tracker.core.repository.queries

import com.dyrkin.tracker.core.driver.AgnosticDriver.api._
import com.dyrkin.tracker.core.repository.DatabaseSupport
import com.dyrkin.tracker.core.repository.Tables._

/**
  * @author eugene zadyra
  */
trait CalendarQueries {
  self: DatabaseSupport =>

  def daysByWorkoutIds(ids: Seq[Long]) = {
    val q = for {
      (c, dof) <- calendars.
        join(daysOfWeek).on((c, dof) => c.id === dof.calendarId)
    } yield (c, dof)
    q.filter { case (c, dof) => c.workoutId inSetBind ids }.map { case (c, dof) => (c.workoutId, dof.day) }
  }
}
