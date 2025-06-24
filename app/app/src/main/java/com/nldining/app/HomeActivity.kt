package com.nldining.app

import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.nldining.app.FilterFragment
import com.nldining.app.ListFragment
import com.nldining.app.MapFragment
import com.nldining.app.R
import com.nldining.app.network.RetrofitClient

class HomeActivity : AppCompatActivity() {

    private lateinit var mapButton: Button
    private lateinit var listButton: Button
    private lateinit var filterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RetrofitClient.initialize(applicationContext)
        mapButton = findViewById(R.id.button_map)
        listButton = findViewById(R.id.button_list)
        filterButton = findViewById(R.id.button_filter)

        replaceFragment(ListFragment())

        mapButton.setOnClickListener {
            replaceFragment(MapFragment())
        }

        listButton.setOnClickListener {
            replaceFragment(ListFragment())
        }

        filterButton.setOnClickListener {
            replaceFragment(FilterFragment())
        }

        // Token ophalen en opslaan
        saveFirebaseToken()
    }

    private fun saveFirebaseToken() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val idToken = task.result?.token
                if (idToken != null) {
                    val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                    prefs.edit().putString("firebase_token", idToken).apply()
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
