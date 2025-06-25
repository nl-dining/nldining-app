package com.nldining.app.models

data class Review(
    val reviewScoreFood: Int,
    val reviewScoreService: Int,
    val reviewScoreAmbiance: Int,
    val lastReview: String
)
