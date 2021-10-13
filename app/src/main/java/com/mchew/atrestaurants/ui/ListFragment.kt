package com.mchew.atrestaurants.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.mchew.atrestaurants.core.BaseFragment
import com.mchew.atrestaurants.core.DataState
import com.mchew.atrestaurants.viewmodel.RestaurantViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.mchew.atrestaurants.databinding.FragmentListBinding as VB

@AndroidEntryPoint
class ListFragment : BaseFragment<VB>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB = VB::inflate

    private val viewModel: RestaurantViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.errorView.retryButton.setOnClickListener {
            viewModel.retryLastRequest()
        }

        viewModel.restaurantsState.observe(viewLifecycleOwner) { dataState ->
            with(binding) {
                when (dataState) {
                    is DataState.Loading -> {
                        loadingScreen.isVisible = true
                        restaurantList.isVisible = false
                        errorScreen.isGone = true
                    }
                    is DataState.Success -> {
                        loadingScreen.isGone = true
                        restaurantList.isVisible = true
                        errorScreen.isGone = true
                        val restaurants = dataState.data
                        restaurantList.adapter = RestaurantAdapter(
                            requireContext(),
                            restaurants,
                            viewModel::setFavoriteStatus
                        ) { RestaurantDetailDialogFragment.navigate(parentFragmentManager, it) }
                    }
                    else -> {
                        loadingScreen.isGone = true
                        restaurantList.isVisible = false
                        errorScreen.isVisible = true
                    }
                }
            }
        }
    }
}