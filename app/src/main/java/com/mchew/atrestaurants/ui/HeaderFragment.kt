package com.mchew.atrestaurants.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
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

        binding.searchBar.setOnKeyListener { _, keyCode, keyEvent ->
            // Pressing Enter key submits search
            if (keyEvent.action == KeyEvent.ACTION_DOWN
                && keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                submitSearch()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        binding.filterButton.setOnClickListener { submitSearch() }
    }

    private fun submitSearch() {
        permissionManager.verifyLocationPermission { isAllowed ->
            if (isAllowed) {
                binding.searchBar.text?.toString()?.let {
                    if (it.isNotBlank()) viewModel.searchForRestaurants(it)
                }
            } else {
                viewModel.showPermissionRequiredError()
            }
        }

        // close keyboard if open
        getSystemService(requireContext(), InputMethodManager::class.java)
            ?.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }
}