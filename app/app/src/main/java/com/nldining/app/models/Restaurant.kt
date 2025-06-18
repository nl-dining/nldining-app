package com.nldining.app.models

data class Restaurant(
    val id: Int,
    val naam: String,
    val categorie: String,
    val tags: String,
    val adres: String,
    val reviewScoreOverall: Int,
    val reviewScoreFood: Int,
    val reviewScoreService: Int,
    val reviewScoreAmbiance: Int,
    val reviewCount: Int,
    val lastReview: String
)
