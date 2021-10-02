package com.simpleapp.challenge.data.remote

import com.simpleapp.challenge.data.model.LoginModel
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

  @POST("login/")
  suspend fun login(@Body model: LoginModel): Result<Boolean>

}