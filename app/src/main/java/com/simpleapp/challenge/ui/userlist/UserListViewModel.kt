package com.simpleapp.challenge.ui.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simpleapp.challenge.data.model.UserDetails
import com.simpleapp.challenge.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
  private val apiService: ApiService
) : ViewModel() {

  sealed class Event {
    data class FailedToFetchUserList(val errorMessage: String?) : Event()
  }

  private val eventChannel = Channel<Event>(Channel.BUFFERED)
  val eventsFlow: Flow<Event> = eventChannel.receiveAsFlow()

  private val _userList = MutableLiveData<List<UserDetails>>()
  val userList: LiveData<List<UserDetails>> = _userList

  private val _userListIsVisible = MutableLiveData(false)
  val userListIsVisible: LiveData<Boolean> = _userListIsVisible

  init {

    viewModelScope.launch {
      try {
        _userList.value = apiService.getUsers()
        _userListIsVisible.value = true
      }
      catch (e: Exception) {
        _userListIsVisible.value = false
        eventChannel.send(Event.FailedToFetchUserList(e.message))
      }
    }

  }

}