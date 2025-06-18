package com.nldining.app.models

data class FilterData(
    val city: String?,
    val category: String?,
    val tags: String?,
    val scoreOverall: Double?,
    val scoreFood: Double?,
    val scoreService: Double?,
    val scoreAmbiance: Double?
)