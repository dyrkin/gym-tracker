package com.dyrkin.tracker.web

import com.dyrkin.tracker.core.repository.Tables
import com.dyrkin.tracker.web.common.WebAuth
import com.dyrkin.tracker.web.common.mock.Mocks
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.FunSuiteLike
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.scalatra.test.scalatest.ScalatraSuite

@RunWith(classOf[JUnitRunner])
class UserControllerSpec extends ScalatraSuite with FunSuiteLike with Mocks with WebAuth with MockitoSugar {

  addServlet(new JsonRoutes(services), "/json/*")

  test("Authenticate") {
    when(services.userService.getUserDetailsById(1L)).thenReturn(Some(Tables.User(Some(1L), "TestName", UserEmail, "hash", "uuid")))
    withSession {
      get("/json/user/current") {
        status should equal(200)
        body should include(UserEmail)
      }
    }
  }

  test("Logout") {
    when(services.userService.getUserDetailsById(1L)).thenReturn(Some(Tables.User(Some(1L), "TestName", UserEmail, "hash", "uuid")))
    withSession {
      get("/json/user/current") {
        status should equal(200)
        body should include(UserEmail)
      }

      get("/json/user/logout") {
        status should equal(204)
      }
    }
  }

  test("Should no access current user without") {
    when(services.userService.getUserDetailsById(1L)).thenReturn(Some(Tables.User(Some(1L), "TestName", UserEmail, "hash", "uuid")))
      get("/json/user/current") {
        status should equal(401)
      }
  }
}
