package com.simpleapp.challenge.ui.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class BaseViewModelTest {

  @ExperimentalCoroutinesApi
  val dispatcher = TestCoroutineDispatcher()

  @get:Rule
  val rule = InstantTaskExecutorRule()

  @ExperimentalCoroutinesApi
  @Before
  fun beforeEach() {
    Dispatchers.setMain(dispatcher)
  }

  @ExperimentalCoroutinesApi
  @After
  fun afterEach() {
    Dispatchers.resetMain()
  }

}