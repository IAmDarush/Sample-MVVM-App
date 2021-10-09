package com.simpleapp.challenge.data.model

data class LoginResponse(
  val isSuccess: Boolean = false,
  val token: String = "",
  val errorCode: Int = 0,
  val errorMessage: String = ""
)