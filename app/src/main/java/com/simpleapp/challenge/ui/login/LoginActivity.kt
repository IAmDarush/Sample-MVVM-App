package com.simpleapp.challenge.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.simpleapp.challenge.R
import com.simpleapp.challenge.databinding.ActivityLoginBinding
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
          LoginViewModel.Event.FailedToValidateAllInputFields -> {
            if (binding.username.text.isEmpty()) {
              binding.username.error = getString(R.string.login_error_username_is_required)
            }

            if (binding.password.text.isEmpty()) {
              binding.password.error = getString(R.string.login_error_password)
            }

            Toast.makeText(
              applicationContext,
              getString(R.string.login_toast_fill_all_inputs),
              Toast.LENGTH_SHORT
            ).show()
          }
          is LoginViewModel.Event.FailedToLogin               -> {
            var errorMessage = event.errorMessage
            if (errorMessage.isEmpty()) errorMessage =
              getString(R.string.login_toast_unknown_server_error)
            Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
          }
          else                                                -> {

          }
        }
      }
    }

  }

}