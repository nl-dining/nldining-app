package com.nldining.app.models

data class RestaurantFilter(
    val id: Int,
    val naam: String,
    val categorie: String,
    val tags: String,
    val adres: String,
    val reviewScoreOverall: Double,
    val reviewScoreFood: Double,
    val reviewScoreService: Double,
    val reviewCount: Int,
    val lastReview: String
)
