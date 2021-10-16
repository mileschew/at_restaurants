package com.mchew.atrestaurants.viewmodel

import android.location.Location
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
import java.lang.IllegalStateException
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val restaurantRepository: RestaurantRepository
) : ViewModel() {

    private val _restaurantsState: MutableLiveData<DataState<List<Restaurant>>> = MutableLiveData()
    val restaurantsState: LiveData<DataState<List<Restaurant>>>
        get() = _restaurantsState

    // used in case of retry
    private var lastRequest: StateEvent = StateEvent.NEARBY
    private var lastSearch: String? = null
    private var lastLocation: Location? = null

    fun fetchRestaurantsNearby(location: Location) = viewModelScope.launch {
        lastRequest = StateEvent.NEARBY
        lastLocation = location
        Timber.d("Beginning search for restaurants around \"${location.latitude},${location.longitude}\"")
        restaurantRepository.getRestaurantsNearby(location)
            .onEach {
                _restaurantsState.value = it
            }.launchIn(viewModelScope)
    }

    fun searchForRestaurants(searchQuery: String) = viewModelScope.launch {
        lastRequest = StateEvent.TEXT_SEARCH
        lastSearch = searchQuery
        Timber.d("Beginning search for \"$searchQuery\"")
        restaurantRepository.getRestaurantsFromSearch(searchQuery)
            .onEach {
                _restaurantsState.value = it
            }.launchIn(viewModelScope)
    }

    fun retryLastRequest() {
        Timber.d("Retrying event: $lastRequest")
        when (lastRequest) {
            StateEvent.NEARBY ->  {
                lastLocation?.let { fetchRestaurantsNearby(it) }
            }
            StateEvent.TEXT_SEARCH -> {
                lastSearch?.let { searchForRestaurants(it) }
            }
        }
    }

    fun showPermissionRequiredError() {
        _restaurantsState.value = DataState.Error(IllegalStateException("Location Permission Required"))
    }

    fun setFavoriteStatus(restaurant: Restaurant, isFavorite: Boolean) = viewModelScope.launch {
        restaurantRepository.updateFavoriteStatus(restaurant, isFavorite)

    }

    private enum class StateEvent {
        NEARBY,
        TEXT_SEARCH
    }
}