package com.simpleapp.challenge.ui.userlist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.simpleapp.challenge.R
import com.simpleapp.challenge.databinding.ActivityUserListBinding
import com.simpleapp.challenge.ui.userlist.UserListViewModel.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class UserListActivity : AppCompatActivity() {

  private lateinit var binding: ActivityUserListBinding
  private val viewModel: UserListViewModel by viewModels()
  private val adapter: UserListRecyclerAdapter by lazy { UserListRecyclerAdapter() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityUserListBinding.inflate(layoutInflater)
    binding.lifecycleOwner = this
    binding.viewModel = viewModel
    setContentView(binding.root)

    lifecycleScope.launchWhenResumed {
      viewModel.eventsFlow.collect { event ->
        when (event) {
          is Event.NavigateToUserDetails -> {
            navigateToUserDetails()
          }
          is Event.FailedToFetchUserList -> {
            val message =
              event.errorMessage ?: getString(R.string.userlist_prompt_failed_to_fetch_user_list)
            Toast.makeText(this@UserListActivity, message, Toast.LENGTH_LONG).show()
          }
        }
      }
    }

    setupRecyclerView()
  }

  private fun setupRecyclerView() {
    val dividerDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
    val dividerDrawable = ContextCompat.getDrawable(this, R.drawable.shape_divider)
    dividerDrawable?.let { dividerDecoration.setDrawable(it) }
    binding.apply {
      rvUserList.addItemDecoration(dividerDecoration)
      rvUserList.adapter = adapter
      val recyclerLayoutManager = LinearLayoutManager(this@UserListActivity)
      rvUserList.layoutManager = recyclerLayoutManager
    }
    viewModel.userList.observe(this, { list ->
      adapter.updateItemsList(list)
    })
  }

  private fun navigateToUserDetails() {
    TODO("navigate to user details")
  }

}