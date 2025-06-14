package com.nldining.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.jvm.java

class SharedViewModelFactory(
    private val nominatimService: NominatimService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SharedViewModel(nominatimService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}