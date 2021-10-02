package com.simpleapp.challenge.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simpleapp.challenge.data.model.LoginModel
import com.simpleapp.challenge.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val apiService: ApiService
) : ViewModel() {

  sealed class Event {
    object FailedToValidateAllInputFields : Event()
    data class FailedToLogin(val errorMessage: String) : Event()
  }

  private val eventChannel = Channel<Event>(Channel.BUFFERED)
  val eventsFlow: Flow<Event> = eventChannel.receiveAsFlow()

  fun login(userName: String, password: String, country: String) {
    val model = LoginModel(userName, password, country)

    when (validateInputs(model)) {
      true  -> {
        viewModelScope.launch {
          val result = apiService.login(model)

          when (result.isSuccess) {
            true  -> {
              TODO("Navigate to user list")
            }
            false -> {
              val message = result.exceptionOrNull()?.message ?: "Login error"
              eventChannel.send(Event.FailedToLogin(message))
            }
          }

        }
      }
      false -> {
        eventChannel.trySend(Event.FailedToValidateAllInputFields)
      }
    }
  }

  private fun validateInputs(model: LoginModel): Boolean {
    return when {
      model.userName.isEmpty() -> false
      model.password.isEmpty() -> false
      else                     -> true
    }
  }

}