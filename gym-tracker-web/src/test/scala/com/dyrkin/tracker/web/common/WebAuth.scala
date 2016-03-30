package com.dyrkin.tracker.web.common

import com.dyrkin.tracker.core.repository.Tables
import com.dyrkin.tracker.web.common.mock.Mocks
import org.mockito.Mockito._
import org.scalatra.test.scalatest.ScalatraSuite

trait WebAuth extends ScalatraSuite with Mocks {
  val UserEmail = "user@user.com"
  val Password = "password"

  def withSession[T](f: => T): T = {
    when(services.userService.userByEmailAndPassword(UserEmail, Password)).thenReturn(Some(Tables.User(Some(1L), "TestName", UserEmail, "hash", "uuid")))
    session {
      post("/json/user/authenticate", "email" -> UserEmail, "password" -> Password) {
        status should equal(200)
      }
      f
    }
  }
}
