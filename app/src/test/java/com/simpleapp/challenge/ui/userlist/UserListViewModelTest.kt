package com.simpleapp.challenge.ui.userlist

import androidx.lifecycle.viewModelScope
import com.simpleapp.challenge.data.model.UserDetails
import com.simpleapp.challenge.data.remote.ApiService
import com.simpleapp.challenge.data.repository.AuthRepository
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
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class UserListViewModelTest : BaseViewModelTest() {

  @Mock
  private lateinit var mockApiService: ApiService

  @Mock
  private lateinit var mockAuthRepository: AuthRepository

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

      vm = UserListViewModel(mockApiService, mockAuthRepository)

      val newUserList = vm.userList.value ?: listOf()

      assertTrue(newUserList.isNotEmpty())
      assertEquals(vm.userListIsVisible.value, true)
      assertEquals(false, vm.showLoading.value)
    }

  @Test
  fun givenTheUserListIsOpened_whenUserListFetchFails_thenShowTheFailureMessage(): Unit =
    runBlocking {
      val errorMessage = "Server error!"
      Mockito.`when`(mockApiService.getUsers())
        .thenAnswer { throw java.lang.Exception(errorMessage) }

      val events = mutableListOf<Event>()

      vm = UserListViewModel(mockApiService, mockAuthRepository)
      vm.viewModelScope.launch {
        vm.eventsFlow.collect { event ->
          events.add(event)
        }
      }

      assertEquals(false, vm.userListIsVisible.value)
      assertEquals(1, events.size)
      assertEquals(Event.FailedToFetchUserList(errorMessage), events[0])
      assertEquals(false, vm.showLoading.value)
    }

  @Test
  fun givenTheUserListIsReady_whenTheUserWantsToSeeTheUserDetails_thenNavigateToUserDetails(): Unit =
    runBlocking {

      Mockito.`when`(mockApiService.getUsers()).thenReturn(users)

      vm = UserListViewModel(mockApiService, mockAuthRepository)
      val userList = vm.userList.value ?: listOf()
      val events = mutableListOf<Event>()
      vm.viewModelScope.launch {
        vm.eventsFlow.collect { event ->
          events.add(event)
        }
      }

      vm.navigateToUserDetails(userList[0])

      assertTrue(events.contains(Event.NavigateToUserDetails(userList[0])))
    }

  @Test
  fun givenTheUserListIsOpened_whenUserWantsToLogOut_thenLogOutAndNavigateToLoginScreen(): Unit =
    runBlocking {
      vm = UserListViewModel(mockApiService, mockAuthRepository)
      val events = mutableListOf<Event>()
      vm.viewModelScope.launch {
        vm.eventsFlow.collect { event -> events.add(event) }
      }

      vm.logOut()

      verify(mockAuthRepository, times(1)).logOutUser()
      verify(mockAuthRepository, times(1)).setUserLoggedOut()
      assertTrue(events.contains(Event.NavigateToLogin))
    }

  @Test
  fun givenTheUserDetailsAreDisplayed_whenTheUserWantsToViewTheLocation_thenShowTheLocation(): Unit =
    runBlocking {
      Mockito.`when`(mockApiService.getUsers()).thenReturn(users)
      vm = UserListViewModel(mockApiService, mockAuthRepository)
      val userList = vm.userList.value ?: listOf()
      val events = mutableListOf<Event>()
      vm.viewModelScope.launch {
        vm.eventsFlow.collect { event -> events.add(event) }
      }
      vm.navigateToUserDetails(userList[0])

      vm.showUserLocation(userList[0])

      assertEquals(Event.NavigateToMaps(userList[0]), events.last())
    }

}