package com.simpleapp.challenge.data.remote

import com.simpleapp.challenge.data.model.LoginModel
import com.simpleapp.challenge.data.model.LoginResponse
import com.simpleapp.challenge.data.model.UserDetails
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) : ApiService {

  override suspend fun login(model: LoginModel): LoginResponse {
    return apiService.login(model)
  }

  override suspend fun getUsers(): List<UserDetails> {
    return apiService.getUsers()
  }


}