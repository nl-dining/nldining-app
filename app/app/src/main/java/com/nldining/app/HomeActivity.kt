package com.nldining.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var mapButton: Button
    private lateinit var listButton: Button
    private lateinit var filterButton: Button
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
