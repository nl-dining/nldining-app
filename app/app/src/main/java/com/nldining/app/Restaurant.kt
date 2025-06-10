package com.nldining.app

import android.R
import kotlin.text.split

data class Restaurant(
    var naam: String,
    var adres: String,
    var categorie: String,
    var tagsInfo: String,
    var scoreOverall: Int,
    var scoreFood: Int,
    var scoreService: Int,
    var scoreAmbiance: Int,
    var lat: Int,
    var lon: Int
) {
    fun SplitTags(): List<String> {
        return tagsInfo.split('|')
    }
}
