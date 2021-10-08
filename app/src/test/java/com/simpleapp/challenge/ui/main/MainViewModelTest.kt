package com.simpleapp.challenge.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.simpleapp.challenge.data.repository.AuthRepository
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
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

  @ExperimentalCoroutinesApi
  val dispatcher = TestCoroutineDispatcher()

  @get:Rule
  val rule = InstantTaskExecutorRule()

  @Mock
  private lateinit var mockAuthRepository: AuthRepository

  private lateinit var vm: MainViewModel
  private val eventsList = mutableListOf<MainViewModel.Event>()

  @ExperimentalCoroutinesApi
  @Before
  fun setup() {
    Dispatchers.setMain(dispatcher)
  }

  @ExperimentalCoroutinesApi
  @After
  fun tearDown() {
    Dispatchers.resetMain()
    eventsList.clear()
  }

  @Test
  fun givenTheAppIsOpened_whenTheUserIsLoggedIn_thenNavigateToUserListScreen(): Unit = runBlocking {
    Mockito.`when`(mockAuthRepository.isUserLoggedIn()).thenReturn(false)

    vm = MainViewModel(mockAuthRepository)
    vm.viewModelScope.launch {
      vm.eventsFlow.collect {
        eventsList.add(it)
      }
    }

    assertEquals(eventsList.size, 1)
    assertEquals(eventsList[0], MainViewModel.Event.NavigateToLogin)
  }

  @Test
  fun givenTheAppIsOpened_whenTheUserIsNotLoggedIn_thenNavigateToLoginScreen() {

  }

}