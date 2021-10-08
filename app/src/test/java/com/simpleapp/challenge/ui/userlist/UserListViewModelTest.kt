package com.simpleapp.challenge.ui.userlist

import com.simpleapp.challenge.data.model.UserDetails
import com.simpleapp.challenge.data.remote.ApiService
import com.simpleapp.challenge.ui.base.BaseViewModelTest
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
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
    }

  @Test
  fun givenTheUserListIsOpened_whenUserListFetchFails_thenShowTheFailureMessage() {

  }

  @Test
  fun givenTheUserListIsReady_whenTheUserWantsToSeeTheUserDetails_thenNavigateToUserDetails() {

  }

}