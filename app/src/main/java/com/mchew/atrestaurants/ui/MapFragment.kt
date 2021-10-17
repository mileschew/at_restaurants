package com.mchew.atrestaurants.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mchew.atrestaurants.R
import com.mchew.atrestaurants.core.BaseFragment
import com.mchew.atrestaurants.core.DataState
import com.mchew.atrestaurants.model.domain.Restaurant
import com.mchew.atrestaurants.viewmodel.RestaurantViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.mchew.atrestaurants.databinding.FragmentMapBinding as VB
import com.google.android.gms.maps.model.LatLngBounds

@AndroidEntryPoint
class MapFragment : BaseFragment<VB>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB = VB::inflate

    private val viewModel: RestaurantViewModel by activityViewModels()
    private lateinit var mapView: SupportMapFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMapView(savedInstanceState)

        binding.errorView.retryButton.setOnClickListener {
            permissionManager.verifyLocationPermission { isAllowed ->
                if (isAllowed) {
                    viewModel.retryLastRequest()
                }
            }
        }

        viewModel.restaurantsState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    binding.errorScreen.isGone = true
                }
                is DataState.Error -> {
                    binding.errorScreen.isVisible = true
                }
                is DataState.Success -> {
                    binding.errorScreen.isGone = true
                    updateMap(dataState.data)
                }
            }
        }
    }

    private fun initMapView(savedInstanceState: Bundle?) {
        mapView = childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment
        mapView.run {
            onCreate(savedInstanceState)
            onResume()
        }
    }

    private fun updateMap(data: List<Restaurant>) {
        mapView.getMapAsync { googleMap ->
            googleMap.clear() // remove any previous markers
            googleMap.setOnInfoWindowClickListener { marker ->
                // Get Restaurant details and present dialog
                (marker.tag as? String)?.let { id ->
                    data.find { it.id == id }?.let { r ->
                        RestaurantDetailDialogFragment.present(parentFragmentManager, r)
                    }
                }
            }

            val boundsBuilder = LatLngBounds.Builder()

            // add markers for results
            data.forEach {
                val coordinates = LatLng(it.coordinates.latitude, it.coordinates.longitude)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(coordinates)
                        .title(it.name)
                        .snippet(it.address)
                )?.tag = it.id
                boundsBuilder.include(coordinates)
            }
            val bounds = boundsBuilder.build()

            // move camera to contain all markers
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
        }
    }
}
