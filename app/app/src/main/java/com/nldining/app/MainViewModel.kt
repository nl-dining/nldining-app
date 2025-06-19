package com.nldining.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nldining.app.models.FilterData
import com.nldining.app.models.Restaurant
import com.nldining.app.network.RetrofitClient
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _selectedRestaurants = MutableLiveData<Set<Restaurant>>(emptySet())
    val selectedRestaurants: LiveData<Set<Restaurant>> get() = _selectedRestaurants

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> = _restaurants

    private val _filterData = MutableLiveData<FilterData>()
    val filterData: LiveData<FilterData> get() = _filterData

    fun setFilter(data: FilterData) {
        _filterData.value = data
        fetchRestaurants(data)
    }

    fun updateSelection(restaurant: Restaurant, selected: Boolean) {
        val current = _selectedRestaurants.value ?: emptySet()
        val updatedSet = current.toMutableSet()

        if (selected) {
            updatedSet.removeAll { it.id == restaurant.id }
            updatedSet.add(restaurant)
        } else {
            updatedSet.removeAll { it.id == restaurant.id }
        }

        _selectedRestaurants.value = updatedSet
    }

    private fun fetchRestaurants(filterData: FilterData) {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.NLDiningAPI.getRestaurants(
                    filterData.city,
                    filterData.category,
                    filterData.tags,
                    filterData.scoreOverall,
                    filterData.scoreFood,
                    filterData.scoreService,
                    filterData.scoreAmbiance
                )
                _restaurants.value = result
            } catch (e: Exception) {
            }
        }
    }
}
