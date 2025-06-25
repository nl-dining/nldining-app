package com.nldining.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.nldining.app.network.RetrofitClient

class HomeActivity : AppCompatActivity() {

    private lateinit var mapButton: Button
    private lateinit var listButton: Button
    private lateinit var filterButton: Button
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RetrofitClient.initialize(applicationContext)

        mapButton = findViewById(R.id.button_map)
        listButton = findViewById(R.id.button_list)
        filterButton = findViewById(R.id.button_filter)
        logoutButton = findViewById(R.id.button_logout)

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

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Close HomeActivity so user can’t go back via back button
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
