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

        viewModel.restaurantsState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    binding.loadingScreen.isVisible = true
                    binding.errorScreen.isGone = true
                }
                is DataState.Success -> {
                    binding.loadingScreen.isGone = true
                    binding.errorScreen.isGone = true
                    val restaurants = it.data
                    binding.restaurantList.adapter = RestaurantAdapter(
                        requireContext(),
                        restaurants,
                        viewModel::setFavoriteStatus
                    )
                }
                else -> {
                    binding.loadingScreen.isGone = true
                    binding.errorScreen.isVisible = true
                }
            }
        }
    }
}