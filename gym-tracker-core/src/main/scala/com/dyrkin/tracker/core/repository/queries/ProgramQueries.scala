package com.dyrkin.tracker.core.repository.queries

import com.dyrkin.tracker.core.repository.DatabaseSupport
import com.dyrkin.tracker.core.repository.Tables._
import com.dyrkin.tracker.core.driver.AgnosticDriver.api._


/**
  * @author eugene zadyra
  */
trait ProgramQueries {
  self: DatabaseSupport =>

  val getActiveProgramByUserIdCompiled = Compiled(getActiveProgramByUserId _)

  private def getActiveProgramByUserId(id: Rep[Long]) = {
    val q = for {
      p <- programs
    } yield p
    q.filter(p => p.isActive === 1 && p.userId === id).map(p => (p.id, p.name))
  }
}
