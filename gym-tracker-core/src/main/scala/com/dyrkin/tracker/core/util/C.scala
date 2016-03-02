package com.dyrkin.tracker.core.util

import com.typesafe.config.ConfigFactory

import scala.concurrent.duration.DurationLong

/**
  * @author eugene zadyra
  */
object C {

  private val config = ConfigFactory.load()

  lazy val pinValidDuration = safe(config.getLong("pin-valid-duration")).getOrElse(60L).minutes
}
