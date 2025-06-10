package com.nldining.app

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NominatimService {

    @Headers("User-Agent: ButtonAdres/1.0 (g.j.bakker@st.hanze.nl)")
    @GET("search")
    suspend fun searchLocation(
        @Query("q") location: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 1
    ): List<NominatimResponse>

}