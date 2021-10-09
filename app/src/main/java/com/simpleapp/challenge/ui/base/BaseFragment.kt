package com.simpleapp.challenge.ui.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment : Fragment() {

  abstract fun getToolbarTitle(): String

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    setToolbarTitle(getToolbarTitle())
    super.onViewCreated(view, savedInstanceState)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      android.R.id.home -> findNavController().popBackStack()
      else              -> super.onOptionsItemSelected(item)
    }
  }

  fun setHomeButtonEnabled(isEnabled: Boolean) {
    (requireActivity() as AppCompatActivity).supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(isEnabled)
      setHomeButtonEnabled(isEnabled)
      setHasOptionsMenu(true)
    }
  }

  private fun setToolbarTitle(title: String) {
    (requireActivity() as AppCompatActivity).supportActionBar?.apply {
      setTitle(title)
    }
  }

  fun setOptionsMenuItemVisible(menu: Menu, @IdRes itemId: Int, isVisible: Boolean) {
    val menuItem = menu.findItem(itemId)
    if (menuItem != null) {
      menuItem.isVisible = isVisible
    }
  }

}