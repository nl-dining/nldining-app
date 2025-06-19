package com.nldining.app

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class HomeActivity : AppCompatActivity() {

    private lateinit var mapButton: Button
    private lateinit var listButton: Button
    private lateinit var filterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
