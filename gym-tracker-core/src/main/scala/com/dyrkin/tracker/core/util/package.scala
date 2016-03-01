package com.dyrkin.tracker.core

import java.util.Date

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
  * @author eugene zadyra
  */
package object util {
  implicit class FutureResult[T](f: => Future[T]) {
    def exec: T = Await.result(f, Duration.Inf)
  }

  def current = new Date()
}
