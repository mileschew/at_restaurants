package com.mchew.atrestaurants.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.mchew.atrestaurants.core.BaseFragment
import com.mchew.atrestaurants.core.DataState
import dagger.hilt.android.AndroidEntryPoint
import com.mchew.atrestaurants.databinding.FragmentListBinding as VB

@AndroidEntryPoint
class ListFragment : BaseFragment<VB>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB = VB::inflate

    private val viewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.restaurantList.observe(viewLifecycleOwner) { result ->
            when (result) {
                is DataState.Loading -> {
                    binding.loading.isVisible = true
                }
                is DataState.Success -> {
                    binding.loading.isGone = true
                    val restaurants = result.data
                    binding.restaurantList.adapter = RestaurantAdapter(restaurants)
                }
                is DataState.Error -> {
                    binding.loading.isGone = true
                }
                else -> { }
            }
        }
    }
}