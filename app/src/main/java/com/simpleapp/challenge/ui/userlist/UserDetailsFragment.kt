package com.simpleapp.challenge.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.simpleapp.challenge.R
import com.simpleapp.challenge.databinding.FragmentUserDetailsBinding
import kotlinx.coroutines.flow.collect
import com.simpleapp.challenge.ui.userlist.UserListViewModel.Event
import kotlinx.coroutines.launch

class UserDetailsFragment : Fragment() {

  lateinit var binding: FragmentUserDetailsBinding
  private val viewModel: UserListViewModel by activityViewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.RESUMED) {
        viewModel.eventsFlow.collect { event ->
          when (event) {
            is Event.NavigateToMaps -> {
              navigateToMaps()
            }
            else                    -> {
              //
            }
          }
        }
      }
    }

  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
    binding.viewModel = viewModel
    binding.user = viewModel.selectedUser.value
    binding.lifecycleOwner = viewLifecycleOwner
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupToolbar()

  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      android.R.id.home -> findNavController().popBackStack()
      else              -> super.onOptionsItemSelected(item)
    }
  }

  override fun onPrepareOptionsMenu(menu: Menu) {
    setOptionsMenuItemVisible(menu, R.id.menu_logout, false)
    super.onPrepareOptionsMenu(menu)
  }

  private fun setOptionsMenuItemVisible(menu: Menu, @IdRes itemId: Int, isVisible: Boolean) {
    val menuItem = menu.findItem(itemId)
    if (menuItem != null) {
      menuItem.isVisible = isVisible
    }
  }

  private fun setupToolbar() {
    (requireActivity() as UserListActivity).supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      setHomeButtonEnabled(true)
      setHasOptionsMenu(true)
      setTitle(R.string.title_fragment_user_details)
    }
  }

  private fun navigateToMaps() {
    UserDetailsFragmentDirections.actionUserDetailsFragmentToMapsFragment().let {
      findNavController().navigate(it)
    }
  }

}