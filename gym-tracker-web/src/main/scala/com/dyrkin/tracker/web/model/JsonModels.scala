package com.dyrkin.tracker.web.model

/**
  * @author ihor zadyra
  */
object JsonModels {
  case class UserLogin(email: String, password: String)
  case class UserRegister(email: String, name: String,  password: String)
}
