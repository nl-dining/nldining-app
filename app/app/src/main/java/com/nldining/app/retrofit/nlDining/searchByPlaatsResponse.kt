package com.nldining.app.retrofit.nlDining

import android.os.Parcel
import android.os.Parcelable

data class SearchByPlaatsResponse(
    val id: Int,
    val naam: String,
    val categorie: String,
    val tags: String,
    val adres: String,
    val reviewScoreOverall: Float,
    val reviewScoreFood: Float,
    val reviewScoreService: Float,
    val reviewScoreAmbiance: Float,
    val reviewCount: Int,
    val lastReview: String
)