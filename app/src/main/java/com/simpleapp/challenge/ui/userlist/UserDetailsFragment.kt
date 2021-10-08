package com.simpleapp.challenge.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.simpleapp.challenge.R
import com.simpleapp.challenge.databinding.FragmentUserDetailsBinding

class UserDetailsFragment : Fragment() {

  lateinit var binding: FragmentUserDetailsBinding
  private val viewModel: UserListViewModel by activityViewModels()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
    binding.viewModel = viewModel
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

  private fun setupToolbar() {
    (requireActivity() as UserListActivity).supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      setHomeButtonEnabled(true)
      setHasOptionsMenu(true)
      setTitle(R.string.title_fragment_user_details)
    }
  }

}