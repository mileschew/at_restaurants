package com.mchew.atrestaurants.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mchew.atrestaurants.core.BaseFragment
import com.mchew.atrestaurants.viewmodel.RestaurantViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.mchew.atrestaurants.databinding.FragmentHeaderBinding as VB

@AndroidEntryPoint
class HeaderFragment : BaseFragment<VB>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB = VB::inflate

    private val viewModel: RestaurantViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            filterButton.setOnClickListener {
                searchBar.text?.toString()?.let {
                    viewModel.restaurantTextSearch(it)
                }
            }
        }
    }
}