package com.simpleapp.challenge.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.simpleapp.challenge.data.model.LoginResponse
import com.simpleapp.challenge.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

  @ExperimentalCoroutinesApi
  val dispatcher = TestCoroutineDispatcher()

  @get:Rule
  val rule = InstantTaskExecutorRule()

  @Mock
  private lateinit var mockApiService: ApiService

  private lateinit var vm: LoginViewModel
  private val eventsList = mutableListOf<LoginViewModel.Event>()

  @ExperimentalCoroutinesApi
  @Before
  fun setup() {
    Dispatchers.setMain(dispatcher)
    vm = LoginViewModel(mockApiService)
    vm.viewModelScope.launch {
      vm.eventsFlow.collect {
        eventsList.add(it)
      }
    }
  }

  @ExperimentalCoroutinesApi
  @After
  fun tearDown() {
    Dispatchers.resetMain()
    eventsList.clear()
  }

  @Test
  fun givenUserClicksLogin_whenUsernameOrPasswordIsEmpty_thenShowRelevantErrorMessage() {
    vm.login("", "", "")

    assertEquals(1, eventsList.size)
    assertEquals(LoginViewModel.Event.FailedToValidateAllInputFields, eventsList[0])
    assertEquals(false, vm.showLoading.value)
  }

  @Test
  fun givenUserClicksLogin_whenUsernameOrPasswordIncorrect_thenShowError(): Unit = runBlocking {
    val userName = "user_name"
    val password = "pass_word"
    val country = "abc"
    val mockResponse =
      LoginResponse(isSuccess = false, errorMessage = "Wrong password", errorCode = 400, token = "")

    doReturn(mockResponse).`when`(mockApiService).login(any())

    vm.login(userName = userName, password = password, country = country)

    assertEquals(1, eventsList.size)
    assertEquals(LoginViewModel.Event.FailedToLogin(mockResponse.errorMessage), eventsList[0])
    assertEquals(false, vm.showLoading.value)
  }

  @Test
  fun givenUserClicksLogin_whenUsernameAndPasswordCorrect_thenNavigateToUserListScreen(): Unit =
    runBlocking {
      val userName = "correct_username"
      val password = "correct_password"
      val country = "the_country"
      val token = "mock_token"
      val mockResponse =
        LoginResponse(isSuccess = true, errorMessage = "", errorCode = 200, token = token)

      doReturn(mockResponse).`when`(mockApiService).login(any())

      vm.login(userName = userName, password = password, country = country)

      assertEquals(1, eventsList.size)
      assertEquals(LoginViewModel.Event.NavigateToUserList, eventsList[0])
      assertEquals(false, vm.showLoading.value)
    }

  @Test
  fun givenUserWantsToLogin_whenServerThrowsException_thenShowTheErrorMessage(): Unit =
    runBlocking {
      val errorMessage = "Server error!"
      `when`(mockApiService.login(any())).thenAnswer { throw java.lang.Exception(errorMessage) }

      vm.login("userName", "Password", "country")

      assertEquals(1, eventsList.size)
      assertEquals(LoginViewModel.Event.FailedToLogin(errorMessage), eventsList[0])
      assertEquals(false, vm.showLoading.value)
    }

}