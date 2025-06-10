package com.nldining.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.collections.isNotEmpty


class LocationViewModel(private val service: NominatimService = RetrofitClient.instance) : ViewModel() {

    private val _result = MutableLiveData<NominatimResponse?>()
    val result: LiveData<NominatimResponse?> = _result

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun searchLocation(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.searchLocation(query)
                if (response.isNotEmpty()) {
                    _result.value = response[0]
                    _error.value = null
                } else {
                    _result.value = null
                    _error.value = "Geen resultaten gevonden."
                }
            } catch (e: Exception) {
                _result.value = null
                _error.value = "Fout: ${e.localizedMessage}"
            }
        }
    }
}