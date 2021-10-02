package com.mchew.atrestaurants.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mchew.atrestaurants.core.DataState
import com.mchew.atrestaurants.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

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