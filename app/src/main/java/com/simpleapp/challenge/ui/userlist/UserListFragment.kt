package com.simpleapp.challenge.ui.userlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.simpleapp.challenge.R
import com.simpleapp.challenge.databinding.FragmentUserListBinding
import com.simpleapp.challenge.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class UserListFragment : Fragment() {

  lateinit var binding: FragmentUserListBinding
  val viewModel: UserListViewModel by activityViewModels()
  private val adapter: UserListRecyclerAdapter by lazy { UserListRecyclerAdapter(viewModel) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    lifecycleScope.launchWhenResumed {
      viewModel.eventsFlow.collect { event ->
        when (event) {
          is UserListViewModel.Event.NavigateToUserDetails -> {
            navigateToUserDetails()
          }
          is UserListViewModel.Event.FailedToFetchUserList -> {
            val message =
              event.errorMessage ?: getString(R.string.userlist_prompt_failed_to_fetch_user_list)
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
          }
          is UserListViewModel.Event.NavigateToLogin       -> {
            navigateToLogin()
          }
        }
      }
    }

  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    binding = FragmentUserListBinding.inflate(inflater, container, false)
    binding.viewModel = viewModel
    binding.lifecycleOwner = viewLifecycleOwner
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupRecyclerView()
  }

  private fun setupRecyclerView() {
    val dividerDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
    val dividerDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.shape_divider)
    dividerDrawable?.let { dividerDecoration.setDrawable(it) }
    binding.apply {
      rvUserList.addItemDecoration(dividerDecoration)
      rvUserList.adapter = adapter
      val recyclerLayoutManager = LinearLayoutManager(requireContext())
      rvUserList.layoutManager = recyclerLayoutManager
    }
    viewModel.userList.observe(viewLifecycleOwner, { list ->
      adapter.updateItemsList(list)
    })
  }

  private fun navigateToLogin() {
    startActivity(Intent(requireContext(), LoginActivity::class.java))
    requireActivity().finish()
  }

  private fun navigateToUserDetails() {
    UserListFragmentDirections.actionUserListFragmentToUserDetailsFragment().let { direction ->
      findNavController().navigate(direction)
    }
  }

}