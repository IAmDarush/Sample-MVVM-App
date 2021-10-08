package com.simpleapp.challenge.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.simpleapp.challenge.databinding.ActivityMainBinding
import com.simpleapp.challenge.ui.login.LoginActivity
import com.simpleapp.challenge.ui.main.MainViewModel.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private val viewModel: MainViewModel by viewModels()
  lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    lifecycleScope.launchWhenResumed {
      viewModel.eventsFlow.collect { event ->
        when (event) {
          Event.NavigateToUserList -> {
            navigateToUserList()
          }
          Event.NavigateToLogin    -> {
            navigateToLogin()
          }
        }
      }
    }
  }

  private fun navigateToLogin() {
    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
    finish()
  }

  private fun navigateToUserList() {
    TODO("navigate to user list")
  }

}