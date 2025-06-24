package com.nldining.app.network

import android.content.Context
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nldining.app.network.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val NLDiningApiUrl = "http://10.0.2.2:5101/api/"
    private const val NominatimApiUrl = "https://nominatim.openstreetmap.org/"

    // Context moet eerst gezet worden
    private lateinit var appContext: Context

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(appContext))
            .build()
    }

    val NLDiningAPI: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(NLDiningApiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    val NominatimApi: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(NominatimApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // Deze methode moet worden aangeroepen in bv. Application of MainActivity om context te zetten
    fun initialize(context: Context) {
        appContext = context.applicationContext
    }

    fun refreshToken(onComplete: (Boolean) -> Unit) {
        val user = Firebase.auth.currentUser
        if (user == null) {
            saveToken(null)
            onComplete(false)
            return
        }
        user.getIdToken(true)
            .addOnSuccessListener { result ->
                saveToken(result.token)
                onComplete(true)
            }
            .addOnFailureListener {
                saveToken(null)
                onComplete(false)
            }
    }

    private fun saveToken(token: String?) {
        val prefs = appContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("firebase_token", token).apply()
    }
}
