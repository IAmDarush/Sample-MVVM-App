package com.simpleapp.challenge.ui.login

import com.simpleapp.challenge.data.remote.ApiService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

  private lateinit var loginViewModel: LoginViewModel

  @Mock
  private lateinit var mockApiService: ApiService

  @Before
  fun setup() {
    loginViewModel = LoginViewModel(mockApiService)
  }

  @Test
  fun givenUserClicksLogin_whenUsernameOrPasswordIsEmpty_thenShowRelevantErrorMessage() {

  }

  @Test
  fun givenUserClicksLogin_whenUsernameOrPasswordIncorrect_thenShowError() {

  }

  @Test
  fun givenUserClicksLogin_whenUsernameAndPasswordCorrect_thenNavigateToUserListScreen() {

  }

}