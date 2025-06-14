package com.nldining.app

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.collections.firstOrNull
import kotlin.collections.toList
import kotlin.let
import kotlin.text.toDouble

class SharedViewModel(private val api: NominatimService) : ViewModel() {

    private val _restaurantList = MutableLiveData<List<Restaurant>>(
        listOf(
            Restaurant(
                "La Piazza",
                "Damrak 1, Amsterdam",
                "Italiaans",
                "lekker,veel,gezond",
                6.00,
                7.00,
                7.00,
                7.00
            ),
            Restaurant(
                "Tokyo Sushi",
                "Spuistraat 3, Amsterdam",
                "Japans",
                "lekker,veel,goed",
                5.00,
                4.00,
                7.00,
                6.00
            ),
            Restaurant(
                "De Frietzaak",
                "Leidsestraat 15, Amsterdam",
                "Snack",
                "lekker,veel,ongezond",
                8.00,
                9.00,
                7.00,
                8.00
            )
        )
    )
    val restaurantList: LiveData<List<Restaurant>> = _restaurantList

    fun toggleRestaurantSelection(restaurant: Restaurant, isSelected: Boolean) {
        restaurant.isSelected = isSelected

        if (isSelected && restaurant.lat == null) {
            viewModelScope.launch {
                try {
                    val result = api.searchLocation(restaurant.address).firstOrNull()
                    result?.let {
                        restaurant.lat = it.lat.toDouble()
                        restaurant.lon = it.lon.toDouble()
                    }
                } catch (_: Exception) {
                    // log eventueel
                } finally {
                    // Trigger LiveData update met kopie
                    _restaurantList.postValue(_restaurantList.value?.toList())
                }
            }
        } else {
            // Trigger directe update
            _restaurantList.value = _restaurantList.value?.toList()
        }
    }
}