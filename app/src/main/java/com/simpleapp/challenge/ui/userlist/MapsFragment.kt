package com.simpleapp.challenge.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.simpleapp.challenge.R
import com.simpleapp.challenge.databinding.FragmentMapsBinding
import com.simpleapp.challenge.ui.base.BaseFragment

class MapsFragment : BaseFragment() {

  lateinit var binding: FragmentMapsBinding
  private val viewModel: UserListViewModel by activityViewModels()

  private val callback = OnMapReadyCallback { googleMap ->
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to
     * install it inside the SupportMapFragment. This method will only be triggered once the
     * user has installed Google Play services and returned to the app.
     */
    viewModel.selectedUser.value?.apply {
      val latitude = address.geo.lat.toDouble()
      val longitude = address.geo.lng.toDouble()
      val position = LatLng(latitude, longitude)
      val title = "${name}'s Location"
      googleMap.addMarker(MarkerOptions().position(position).title(title))
      googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
    }
  }

  override fun getToolbarTitle(): String {
    return getString(R.string.title_fragment_user_location)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View {
    binding = FragmentMapsBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setHomeButtonEnabled(true)

    val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
    mapFragment?.getMapAsync(callback)
  }

  override fun onPrepareOptionsMenu(menu: Menu) {
    setOptionsMenuItemVisible(menu, R.id.menu_logout, false)
    super.onPrepareOptionsMenu(menu)
  }

}