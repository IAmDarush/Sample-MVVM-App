package com.simpleapp.challenge.data.remote

import com.simpleapp.challenge.data.model.LoginModel
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) : ApiService {

  override suspend fun login(model: LoginModel): Result<Boolean> {
    return apiService.login(model)
  }


}