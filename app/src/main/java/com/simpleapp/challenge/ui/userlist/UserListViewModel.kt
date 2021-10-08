package com.simpleapp.challenge.ui.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simpleapp.challenge.data.model.UserDetails
import com.simpleapp.challenge.data.remote.ApiService
import com.simpleapp.challenge.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
  private val apiService: ApiService,
  private val authRepository: AuthRepository
) : ViewModel() {

  sealed class Event {
    object NavigateToLogin : Event()
    data class FailedToFetchUserList(val errorMessage: String?) : Event()
    data class NavigateToUserDetails(val userDetails: UserDetails) : Event()
  }

  private val eventChannel = Channel<Event>(Channel.BUFFERED)
  val eventsFlow: Flow<Event> = eventChannel.receiveAsFlow()

  private val _userList = MutableLiveData<List<UserDetails>>()
  val userList: LiveData<List<UserDetails>> = _userList

  private val _userListIsVisible = MutableLiveData(false)
  val userListIsVisible: LiveData<Boolean> = _userListIsVisible

  private val _showLoading = MutableLiveData(false)
  val showLoading: LiveData<Boolean> = _showLoading

  private val _selectedUser = MutableLiveData<UserDetails>()
  val selectedUser: LiveData<UserDetails> = _selectedUser

  init {

    viewModelScope.launch {
      _showLoading.value = true
      try {
        _userList.value = apiService.getUsers()
        Timber.d("Fetched user list with size ${userList.value?.size}")
        _userListIsVisible.value = true
      }
      catch (e: Exception) {
        Timber.e(e, "Failed to fetch the user list")
        _userListIsVisible.value = false
        eventChannel.send(Event.FailedToFetchUserList(e.message))
      }
      _showLoading.value = false
    }

  }

  fun navigateToUserDetails(userDetails: UserDetails) {
    viewModelScope.launch {
      _selectedUser.value = userDetails
      eventChannel.send(Event.NavigateToUserDetails(userDetails))
    }
  }

  fun logOut() {
    viewModelScope.launch {
      authRepository.logOutUser()
      authRepository.setUserLoggedOut()
      Timber.d("User logged out")
      eventChannel.send(Event.NavigateToLogin)
    }
  }

}