package com.simpleapp.challenge.data.remote

import com.simpleapp.challenge.data.model.LoginModel
import com.simpleapp.challenge.data.model.LoginResponse
import com.simpleapp.challenge.data.model.UserDetails
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

  @POST("login/")
  suspend fun login(@Body model: LoginModel): LoginResponse

  @GET("https://jsonplaceholder.typicode.com/users")
  suspend fun getUsers(): List<UserDetails>

}