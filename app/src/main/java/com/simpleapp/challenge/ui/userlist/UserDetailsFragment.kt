package com.simpleapp.challenge.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.simpleapp.challenge.R
import com.simpleapp.challenge.databinding.FragmentUserDetailsBinding
import com.simpleapp.challenge.ui.base.BaseFragment
import com.simpleapp.challenge.ui.userlist.UserListViewModel.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserDetailsFragment : BaseFragment() {

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

  override fun getToolbarTitle(): String {
    return getString(R.string.title_fragment_user_details)
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

    setHomeButtonEnabled(true)
  }

  override fun onPrepareOptionsMenu(menu: Menu) {
    setOptionsMenuItemVisible(menu, R.id.menu_logout, false)
    super.onPrepareOptionsMenu(menu)
  }

  private fun navigateToMaps() {
    UserDetailsFragmentDirections.actionUserDetailsFragmentToMapsFragment().let {
      findNavController().navigate(it)
    }
  }

}