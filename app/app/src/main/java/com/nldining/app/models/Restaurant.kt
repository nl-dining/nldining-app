package com.nldining.app.models

import java.io.Serializable

data class Restaurant(
    val id: Int,
    val naam: String,
    val categorie: String,
    val tags: String,
    val adres: String,
    val reviewScoreOverall: Double,
    val reviewScoreFood: Double,
    val reviewScoreService: Double,
    val reviewCount: Int,
    val lastReview: String,

    val latitude: Double? = null,
    val longitude: Double? = null
) : Serializable
