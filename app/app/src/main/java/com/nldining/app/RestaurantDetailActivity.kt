package com.nldining.app

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nldining.app.databinding.ActivityRestaurantDetailBinding
import com.nldining.app.models.Restaurant
import java.util.Locale

class RestaurantDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantDetailBinding
    private var currentRestaurantId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Haal het Restaurant-object uit de Intent
        val restaurant = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("restaurant", Restaurant::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("restaurant") as? Restaurant
        }
        restaurant?.let {
            currentRestaurantId = it.id
            binding.textRestaurantName.text = it.naam
            binding.textAddress.text = it.adres
            // rating bar max 5 sterren, aanpassen naar wens
            binding.detailRatingBar.rating = (it.reviewScoreOverall / 2).toFloat()

            binding.category.text = it.categorie
            binding.tags.text = it.tags
            binding.textScore.text = getString(R.string.total_score, it.reviewScoreOverall)

            binding.textReviewScoreFood.text = String.format(Locale.getDefault(), "%.1f", it.reviewScoreFood)
            binding.textReviewScoreService.text = String.format(Locale.getDefault(), "%.1f", it.reviewScoreService)

            binding.textLastReview.text = it.lastReview
        }

        binding.buttonAddReview.setOnClickListener {
            val intent = Intent(this, ReviewActivity::class.java)
            intent.putExtra("restaurantId", currentRestaurantId)
            startActivity(intent)
        }

    }
}
