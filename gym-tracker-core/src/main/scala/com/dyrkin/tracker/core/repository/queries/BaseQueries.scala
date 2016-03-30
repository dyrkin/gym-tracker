package com.dyrkin.tracker.core.repository.queries

import com.dyrkin.tracker.core.repository.DatabaseSupport
import com.dyrkin.tracker.core.driver.AgnosticDriver.api._
import com.dyrkin.tracker.core.repository.Tables.TableWithId

/**
  * Created by ezadyra on 3/30/2016.
  */
trait BaseQueries[Z] {
  self: DatabaseSupport =>

  def insertOrUpdate[T <: Table[Z] with TableWithId](tableQuery: TableQuery[T], value: Z) = {
    (tableQuery returning tableQuery.map(_.id)) insertOrUpdate value
  }
}
