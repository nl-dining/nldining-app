package com.nldining.app.retrofit.nlDining

import com.nldining.app.models.Restaurant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantApi {
    @GET("Restaurants/filter")
    suspend fun searchRestaurants(
        @Query("plaats") plaats: String?,
        @Query("categorie") categorie: String?,
        @Query("tags") tags: String?,
        @Query("reviewScoreOverall") reviewScoreOverall: String?,
        @Query("reviewScoreFood") reviewScoreFood: String?,
        @Query("reviewScoreService") reviewScoreService: String?,
        @Query("reviewScoreAmbiance") reviewScoreAmbiance: String?
    ): List<SearchByPlaatsResponse>
}
