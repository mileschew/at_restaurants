package com.mchew.atrestaurants.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mchew.atrestaurants.core.BaseFragment
import com.mchew.atrestaurants.core.DataState
import com.mchew.atrestaurants.viewmodel.RestaurantViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.mchew.atrestaurants.databinding.FragmentMapBinding as VB

@AndroidEntryPoint
class MapFragment : BaseFragment<VB>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB = VB::inflate

    private val viewModel: RestaurantViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.restaurantsState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> { }
                is DataState.Error -> { }
                is DataState.Success -> { }
            }
        }
    }
}