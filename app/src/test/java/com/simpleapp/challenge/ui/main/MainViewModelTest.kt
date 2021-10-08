package com.simpleapp.challenge.ui.main

import androidx.lifecycle.viewModelScope
import com.simpleapp.challenge.data.repository.AuthRepository
import com.simpleapp.challenge.ui.base.BaseViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest : BaseViewModelTest() {

  @Mock
  private lateinit var mockAuthRepository: AuthRepository

  private lateinit var vm: MainViewModel
  private val eventsList = mutableListOf<MainViewModel.Event>()

  @ExperimentalCoroutinesApi
  @After
  fun tearDown() {
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
  fun givenTheAppIsOpened_whenTheUserIsNotLoggedIn_thenNavigateToLoginScreen(): Unit = runBlocking {
    Mockito.`when`(mockAuthRepository.isUserLoggedIn()).thenReturn(true)

    vm = MainViewModel(mockAuthRepository)
    vm.viewModelScope.launch {
      vm.eventsFlow.collect {
        eventsList.add(it)
      }
    }

    assertEquals(eventsList.size, 1)
    assertEquals(eventsList[0], MainViewModel.Event.NavigateToUserList)
  }

}