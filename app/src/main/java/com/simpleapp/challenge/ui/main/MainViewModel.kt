package com.simpleapp.challenge.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simpleapp.challenge.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

  sealed class Event {
    object NavigateToLogin : Event()
    object NavigateToUserList: Event()
  }

  private val eventChannel = Channel<Event>(Channel.BUFFERED)
  val eventsFlow: Flow<Event> = eventChannel.receiveAsFlow()

  init {

    viewModelScope.launch {
      when (authRepository.isUserLoggedIn()) {
        false -> {
          eventChannel.send(Event.NavigateToLogin)
        }
        true  -> {
          eventChannel.send(Event.NavigateToUserList)
        }
      }
    }

  }

}