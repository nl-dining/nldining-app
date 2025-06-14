package com.nldining.app

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import kotlin.jvm.java

interface NominatimService {

    @Headers("User-Agent: ButtonAdres/1.0 (g.j.bakker@st.hanze.nl)")
    @GET("search")
    suspend fun searchLocation(
        @Query("q") location: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 1
    ): List<NominatimResponse>

    companion object {
        fun create(): NominatimService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://nominatim.openstreetmap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(NominatimService::class.java)
        }
    }
}