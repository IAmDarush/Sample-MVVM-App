package com.simpleapp.challenge.ui.login

import androidx.lifecycle.viewModelScope
import com.simpleapp.challenge.data.model.User
import com.simpleapp.challenge.data.repository.AuthRepository
import com.simpleapp.challenge.ui.base.BaseViewModelTest
import com.simpleapp.challenge.ui.login.LoginViewModel.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest: BaseViewModelTest() {

  @Mock
  private lateinit var mockAuthRepository: AuthRepository

  private lateinit var vm: LoginViewModel
  private val eventsList = mutableListOf<Event>()

  @ExperimentalCoroutinesApi
  @Before
  fun setup() {
    vm = LoginViewModel(mockAuthRepository)
    vm.viewModelScope.launch {
      vm.eventsFlow.collect {
        eventsList.add(it)
      }
    }
  }

  @ExperimentalCoroutinesApi
  @After
  fun tearDown() {
    eventsList.clear()
  }

  @Test
  fun givenUserClicksLogin_whenUsernameOrPasswordIsEmpty_thenShowInputValidationErrorMessage() {
    vm.login("", "", "")

    assertEquals(1, eventsList.size)
    assertEquals(Event.FailedToValidateAllInputFields, eventsList[0])
    assertEquals(false, vm.showLoading.value)
  }

  @Test
  fun givenUserClicksLogin_whenPasswordIsIncorrect_thenShowPasswordIncorrectError(): Unit =
    runBlocking {
      val user = User.create("john", "1234", "UK")
      doReturn(user).`when`(mockAuthRepository).getUserByUsername(user.username)

      vm.login(userName = "john", password = "1235", country = "UK")

      assertEquals(1, eventsList.size)
      assertEquals(Event.IncorrectPassword, eventsList[0])
      assertEquals(false, vm.showLoading.value)
      Mockito.verify(mockAuthRepository, Mockito.times(1)).getUserByUsername("john")
    }

  @Test
  fun givenUserClicksLogin_whenUsernameAndPasswordCorrect_thenNavigateToUserListScreen(): Unit =
    runBlocking {
      val user = User.create("john", "1234", "UK")
      doReturn(user).`when`(mockAuthRepository).getUserByUsername(user.username)

      vm.login(userName = "john", password = "1234", country = "UK")

      assertEquals(2, eventsList.size)
      assertEquals(Event.SuccessfullyLoggedIn, eventsList[0])
      assertEquals(Event.NavigateToUserList, eventsList[1])
      assertEquals(false, vm.showLoading.value)
      Mockito.verify(mockAuthRepository, Mockito.times(1)).getUserByUsername("john")
    }

  @Test
  fun givenUserClicksLogin_whenUserIsNotAlreadyRegistered_thenRegisterTheUserAndNavigateToUserListScreen(): Unit =
    runBlocking {
      val user = User.create("john", "1234", "UK")
      doReturn(null).`when`(mockAuthRepository).getUserByUsername(user.username)

      vm.login(userName = "john", password = "1234", country = "UK")

      assertEquals(2, eventsList.size)
      assertEquals(Event.SuccessfullyRegistered, eventsList[0])
      assertEquals(Event.NavigateToUserList, eventsList[1])
      assertEquals(false, vm.showLoading.value)
      Mockito.verify(mockAuthRepository, Mockito.times(1)).getUserByUsername("john")
      Mockito.verify(mockAuthRepository, Mockito.times(1)).registerUser(user)
    }

  @Test
  fun givenUserIsSuccessfullyLoggedIn_whenUserWantsToRememberLogin_thenSaveTheLoginState(): Unit =
    runBlocking {
      val user = User.create("john", "1234", "UK")
      doReturn(user).`when`(mockAuthRepository).getUserByUsername(user.username)

      vm.login(userName = "john", password = "1234", country = "UK", rememberLogin = true)

      Mockito.verify(mockAuthRepository, Mockito.times(1)).setUserLoggedIn()
      assertEquals(2, eventsList.size)
      assertEquals(Event.SuccessfullyLoggedIn, eventsList[0])
      assertEquals(Event.NavigateToUserList, eventsList[1])
      assertEquals(false, vm.showLoading.value)
      Mockito.verify(mockAuthRepository, Mockito.times(1)).getUserByUsername("john")
    }

  @Test
  fun givenUserIsSuccessfullyRegistered_whenUserWantsToRememberLogin_thenSaveTheLoginState(): Unit =
    runBlocking {
      val user = User.create("john", "1234", "UK")
      doReturn(null).`when`(mockAuthRepository).getUserByUsername(user.username)

      vm.login(userName = "john", password = "1234", country = "UK", rememberLogin = true)

      Mockito.verify(mockAuthRepository, Mockito.times(1)).setUserLoggedIn()
      assertEquals(2, eventsList.size)
      assertEquals(Event.SuccessfullyRegistered, eventsList[0])
      assertEquals(Event.NavigateToUserList, eventsList[1])
      assertEquals(false, vm.showLoading.value)
      Mockito.verify(mockAuthRepository, Mockito.times(1)).getUserByUsername("john")
    }

}