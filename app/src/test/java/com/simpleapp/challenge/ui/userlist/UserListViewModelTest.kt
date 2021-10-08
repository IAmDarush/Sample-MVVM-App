package com.simpleapp.challenge.ui.userlist

import com.simpleapp.challenge.ui.base.BaseViewModelTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserListViewModelTest: BaseViewModelTest() {

  private lateinit var vm: UserListViewModel

  @Test
  fun givenTheUserListIsOpened_whenUserListIsFetchedSuccessfully_thenShowTheUserList() {

  }

  @Test
  fun givenTheUserListIsOpened_whenUserListFetchFails_thenShowTheFailureMessage() {

  }

  @Test
  fun givenTheUserListIsReady_whenTheUserWantsToSeeTheUserDetails_thenNavigateToUserDetails() {

  }

}