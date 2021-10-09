package com.mchew.atrestaurants.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mchew.atrestaurants.core.DataState
import com.mchew.atrestaurants.model.domain.Restaurant
import com.mchew.atrestaurants.repository.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val restaurantRepository: RestaurantRepository
) : ViewModel() {

    private val _restaurantsState: MutableLiveData<DataState<List<Restaurant>>> = MutableLiveData()
    val restaurantsState: LiveData<DataState<List<Restaurant>>>
        get() = _restaurantsState

    fun fetchRestaurantsNearby()  = viewModelScope.launch {
        // TODO replace with call to fetch nearby
        restaurantRepository.getRestaurants("Beaverton")
            .onEach {
                _restaurantsState.value = it
            }.launchIn(viewModelScope)
    }

    fun restaurantTextSearch(searchQuery: String) = viewModelScope.launch {
        Timber.d("Beginning search for \"$searchQuery\"")
        restaurantRepository.getRestaurants(searchQuery)
            .onEach {
                _restaurantsState.value = it
            }.launchIn(viewModelScope)
    }
}