package com.simpleapp.challenge.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.simpleapp.challenge.R
import com.simpleapp.challenge.databinding.ActivityLoginBinding
import com.simpleapp.challenge.ui.login.LoginViewModel.Event
import com.simpleapp.challenge.ui.userlist.UserListActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

  private val viewModel: LoginViewModel by viewModels()
  private lateinit var binding: ActivityLoginBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityLoginBinding.inflate(layoutInflater)
    binding.lifecycleOwner = this
    binding.viewModel = viewModel
    setContentView(binding.root)

    lifecycleScope.launchWhenResumed {
      viewModel.eventsFlow.collect { event ->
        when (event) {
          Event.FailedToValidateAllInputFields -> {
            if (binding.username.text.isEmpty()) {
              binding.username.error = getString(R.string.login_error_username_is_required)
            }

            if (binding.password.text.isEmpty()) {
              binding.password.error = getString(R.string.login_error_password)
            }

            val message = getString(R.string.login_toast_fill_all_inputs)
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
          }
          is Event.FailedToLogin               -> {
            var errorMessage = event.errorMessage
            if (errorMessage.isEmpty()) errorMessage =
              getString(R.string.login_toast_unknown_server_error)
            Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
          }
          is Event.IncorrectPassword           -> {
            val message = getString(R.string.login_prompt_incorrect_password)
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
          }
          is Event.SuccessfullyRegistered      -> {
            val message = getString(R.string.login_prompt_register_successful)
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
          }
          is Event.SuccessfullyLoggedIn        -> {
            val message = getString(R.string.login_prompt_login_successful)
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
          }
          is Event.NavigateToUserList          -> {
            navigateToUserList()
          }
        }
      }
    }

  }

  private fun navigateToUserList() {
    startActivity(Intent(this, UserListActivity::class.java))
    finish()
  }

}