package com.nldining.app

import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.text.format

class RestaurantDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_restaurant)

        val name = intent.getStringExtra("name") ?: ""
        val address = intent.getStringExtra("address") ?: ""
        val category = intent.getStringExtra("category") ?: ""
        val tags = intent.getStringExtra("tags") ?: ""
        val overall = intent.getDoubleExtra("reviewScoreOverall", 0.0)
        val food = intent.getDoubleExtra("reviewScoreFood", 0.0)
        val service = intent.getDoubleExtra("reviewScoreService", 0.0)
        val ambiance = intent.getDoubleExtra("reviewScoreAmbiance", 0.0)

        // UI references
        findViewById<TextView>(R.id.text_restaurant_name).text = name
        findViewById<TextView>(R.id.text_address).text = address
        findViewById<TextView>(R.id.categorie).text = category
        findViewById<TextView>(R.id.tags).text = tags
        findViewById<TextView>(R.id.text_score).text = "Total Score: ${"%.1f".format(overall)} / 10"
        findViewById<TextView>(R.id.text_reviewScoreFood).text = "%.1f".format(food)
        findViewById<TextView>(R.id.text_reviewScoreService).text = "%.1f".format(service)
        findViewById<TextView>(R.id.text_reviewScoreAmbiance).text = "%.1f".format(ambiance)
        findViewById<RatingBar>(R.id.detailRatingBar).rating = (overall / 2).toFloat()

        // Optioneel: Add Review knop
        findViewById<Button>(R.id.button_add_review).setOnClickListener {
            // Hier kun je eventueel navigatie of een formulier aan koppelen
        }
    }
}