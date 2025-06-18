package com.nldining.app.retrofit

import com.nldining.app.retrofit.nlDining.RestaurantApi
import com.nldining.app.retrofit.norminatim.NominatimService
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import kotlin.jvm.java

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:5101/api/"

    val instance: RestaurantApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(RestaurantApi::class.java)
    }

//
}