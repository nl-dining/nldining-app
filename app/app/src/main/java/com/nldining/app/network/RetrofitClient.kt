package com.nldining.app.network

//import com.google.android.gms.common.api.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object RetrofitClient {
    private const val NLDiningApiUrl = "http://10.0.2.2:5101/api/"
    private const val NominatimApiUrl = "https://nominatim.openstreetmap.org/"

    val NLDiningAPI: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(NLDiningApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }

    val NominatimApi: ApiService by lazy {
        var retrofit = Retrofit.Builder()
            .baseUrl(NominatimApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }

}