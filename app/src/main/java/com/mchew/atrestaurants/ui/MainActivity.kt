package com.mchew.atrestaurants.ui

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mchew.atrestaurants.R
import com.mchew.atrestaurants.core.PermissionManager
import com.mchew.atrestaurants.databinding.ActivityMainBinding
import com.mchew.atrestaurants.viewmodel.RestaurantViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var permissionManager: PermissionManager

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val navController by lazy {
        findNavController(R.id.nav_host_fragment)
    }
    private val viewModel: RestaurantViewModel by viewModels()

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.viewToggleButton.setOnClickListener {
            when (navController.currentDestination?.id) {
                R.id.navigation_list -> toggleViewToMap()
                else -> toggleViewToList()
            }
        }
        permissionManager.verifyLocationPermission(this) { isAllowed ->
            if (isAllowed) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location ->
                        // Got last known location. In some rare situations this can be null.
                        viewModel.fetchRestaurantsNearby(location)
                    }.addOnFailureListener {
                        viewModel.showPermissionRequiredError()
                    }
            } else {
                viewModel.showPermissionRequiredError()
            }
        }
    }

    private fun toggleViewToMap() {
        navController.navigate(ListFragmentDirections.actionListToMap())
        // set toggle button to List
        with(binding.viewToggleButton) {
            text = getString(R.string.cta_list)
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_outline_format_list_bulleted_24, 0, 0, 0)
        }
    }

    private fun toggleViewToList() {
        navController.navigate(MapFragmentDirections.actionMapToList())
        // set toggle button to Map
        with(binding.viewToggleButton) {
            text = getString(R.string.cta_map)
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_outline_location_on_24, 0, 0, 0)
        }
    }
}