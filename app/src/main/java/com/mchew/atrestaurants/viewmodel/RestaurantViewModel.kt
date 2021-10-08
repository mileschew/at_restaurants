package com.mchew.atrestaurants.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mchew.atrestaurants.repository.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val restaurantRepository: RestaurantRepository
) : ViewModel() {

    val restaurantsState = liveData {
        restaurantRepository.getRestaurants("Beaverton Bar").collect {
            emit(it)
        }
    }
}