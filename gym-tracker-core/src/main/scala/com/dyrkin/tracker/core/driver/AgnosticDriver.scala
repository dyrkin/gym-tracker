package com.dyrkin.tracker.core.driver

/**
  * @author eugene zadyra
  */

import com.typesafe.config.ConfigFactory

import slick.driver.{H2Driver, JdbcDriver, MySQLDriver, PostgresDriver}

import scala.util.Try

object AgnosticDriver {

  val api = profile.api

  lazy val profile: JdbcDriver = {
    val config = ConfigFactory.load()
    Try(config.getString("database-ref")) map { ref =>
      config.getString(s"$ref.driver") match {
        case "org.h2.Driver" => H2Driver
        case "com.mysql.jdbc.Driver" => MySQLDriver
        case "org.postgresql.Driver" => PostgresDriver
      }
    } getOrElse H2Driver
  }
}
