package com.simpleapp.challenge.ui.userlist

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor() : ViewModel() {

  sealed class Event {

  }

  private val eventChannel = Channel<Event>(Channel.BUFFERED)
  val eventsFlow: Flow<Event> = eventChannel.receiveAsFlow()

}