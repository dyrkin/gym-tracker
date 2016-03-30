package com.dyrkin.tracker.web.common.mock

import com.dyrkin.tracker.core.service.{ProgramService, UserService, PinService, ServicesT}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar

trait Mocks extends MockitoSugar {

  //mocks
  private val pinService = mock[PinService]
  private val userService = mock[UserService]
  private val programService = mock[ProgramService]
  val services = mock[ServicesT]

  //behaviour
  when(services.pinService).thenReturn(pinService)
  when(services.userService).thenReturn(userService)
  when(services.programService).thenReturn(programService)
}
