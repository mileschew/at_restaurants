package com.mchew.atrestaurants.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.mchew.atrestaurants.R
import com.mchew.atrestaurants.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewToggleButton.setOnClickListener {
            when (navController.currentDestination?.id) {
                R.id.navigation_list -> toggleViewToMap()
                else -> toggleViewToList()
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