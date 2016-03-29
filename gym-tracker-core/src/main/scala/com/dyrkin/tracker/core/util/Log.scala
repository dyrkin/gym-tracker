package com.dyrkin.tracker.core.util

import org.slf4j.LoggerFactory

trait Log {
  val LOGGER = LoggerFactory.getLogger(this.getClass.getCanonicalName)
  LOGGER.info(this.getClass.getName)

  protected def info(m: => String) = {
    if (LOGGER.isInfoEnabled)
      LOGGER.info(m)
  }

  protected def debug(m: => String) = {
    if (LOGGER.isDebugEnabled)
      LOGGER.debug(m)
  }

  protected def error(m: => String) = {
    if (LOGGER.isErrorEnabled)
      LOGGER.error(m)
  }

  protected def error(m: => String, throwable: Throwable) = {
    if (LOGGER.isErrorEnabled)
      LOGGER.error(m, throwable)
  }
}
