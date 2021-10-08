package com.simpleapp.challenge.ui.userlist

import androidx.lifecycle.viewModelScope
import com.simpleapp.challenge.data.model.UserDetails
import com.simpleapp.challenge.data.remote.ApiService
import com.simpleapp.challenge.ui.base.BaseViewModelTest
import com.simpleapp.challenge.ui.userlist.UserListViewModel.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserListViewModelTest : BaseViewModelTest() {

  @Mock
  private lateinit var mockApiService: ApiService

  private val userDetailsJson: String by lazy {
    ClassLoader.getSystemResource("user-details.json").readText()
  }

  private val users: List<UserDetails> by lazy {
    val format = Json { ignoreUnknownKeys = true; isLenient = true }
    format.decodeFromString(userDetailsJson)
  }

  private lateinit var vm: UserListViewModel

  @Test
  fun givenTheUserListIsOpened_whenUserListIsFetchedSuccessfully_thenShowTheUserList(): Unit =
    runBlocking {
      Mockito.`when`(mockApiService.getUsers()).thenReturn(users)

      vm = UserListViewModel(mockApiService)

      val newUserList = vm.userList.value ?: listOf()

      assertTrue(newUserList.isNotEmpty())
      assertEquals(vm.userListIsVisible.value, true)
    }

  @Test
  fun givenTheUserListIsOpened_whenUserListFetchFails_thenShowTheFailureMessage(): Unit =
    runBlocking {
      val errorMessage = "Server error!"
      Mockito.`when`(mockApiService.getUsers()).thenAnswer { throw java.lang.Exception(errorMessage) }

      val events = mutableListOf<Event>()

      vm = UserListViewModel(mockApiService)
      vm.viewModelScope.launch {
        vm.eventsFlow.collect { event ->
          events.add(event)
        }
      }

      assertEquals(false, vm.userListIsVisible.value)
      assertEquals(1, events.size)
      assertEquals(Event.FailedToFetchUserList(errorMessage), events[0])
    }

  @Test
  fun givenTheUserListIsReady_whenTheUserWantsToSeeTheUserDetails_thenNavigateToUserDetails() {

  }

}