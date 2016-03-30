package com.dyrkin.tracker.core

import java.util.{UUID, Date}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.Try

/**
  * @author eugene zadyra
  */
package object util {
  implicit class FutureResult[T](f: => Future[T]) {
    def exec: T = Await.result(f, Duration.Inf)
  }

  def current = new Date()

  def uuid = UUID.randomUUID().toString

  def safe[T](f: => T): Option[T] = {
    Try(f).toOption
  }

  implicit class OptionString(stringOpt: Option[String]) {
    def noneIfEmpty = stringOpt.filterNot(_.isEmpty)
  }

  def noId = sys.error("Id is not defined")
}
