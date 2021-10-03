package com.simpleapp.challenge.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    object NavigateToUserList : Event()
  }

  private val eventChannel = Channel<Event>(Channel.BUFFERED)
  val eventsFlow: Flow<Event> = eventChannel.receiveAsFlow()

  private val _showLoading = MutableLiveData(false)
  val showLoading: LiveData<Boolean> = _showLoading

  fun login(userName: String, password: String, country: String) {
    _showLoading.value = true

    val model = LoginModel(userName, password, country)

    when (validateInputs(model)) {
      true  -> {
        viewModelScope.launch {

          try {
            val result = apiService.login(model)

            when (result.isSuccess) {
              true  -> {
                eventChannel.send(Event.NavigateToUserList)
              }
              false -> {
                eventChannel.send(Event.FailedToLogin(result.errorMessage))
              }
            }

          }
          catch (e: Exception) {
            eventChannel.send(Event.FailedToLogin(e.message ?: ""))
          }
          _showLoading.value = false

        }
      }
      false -> {
        _showLoading.value = false
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