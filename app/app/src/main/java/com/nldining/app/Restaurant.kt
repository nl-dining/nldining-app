package com.nldining.app

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurant(
    val name: String,
    val address: String,
    val category: String,
    val tags: String,
    val reviewScoreOverall: Double,
    val reviewScoreFood: Double,
    val reviewScoreService: Double,
    val reviewScoreAmbiance: Double,
    var isSelected: Boolean = false,
    var lat: Double? = null,
    var lon: Double? = null
) : Parcelable
// {
//    fun SplitTags(): List<String> {
//        return tagsInfo.split('|')
//    }
//}
