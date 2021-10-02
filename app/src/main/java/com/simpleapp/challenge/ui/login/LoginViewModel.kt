package com.simpleapp.challenge.ui.login

import androidx.lifecycle.ViewModel
import com.simpleapp.challenge.data.model.LoginModel
import com.simpleapp.challenge.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val apiService: ApiService
) : ViewModel() {

  fun login(model: LoginModel) {
    TODO("Not yet implemented")
  }

}