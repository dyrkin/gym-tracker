package com.dyrkin.tracker.core.driver

/**
  * @author eugene zadyra
  */
import com.typesafe.config.ConfigFactory

import slick.driver.{H2Driver, JdbcDriver, MySQLDriver, PostgresDriver}

object AgnosticDriver {

  val api = profile.api

  lazy val profile: JdbcDriver = {
    sys.env.get("DB_ENVIRONMENT") match {
      case Some(e) => ConfigFactory.load().getString(s"$e.driver") match {
        case "org.h2.Driver" => H2Driver
        case "slick.driver.MySQLDriver$" => MySQLDriver
        case "slick.driver.PostgresDriver$" => PostgresDriver
      }
      case _ => H2Driver
    }
  }
}
