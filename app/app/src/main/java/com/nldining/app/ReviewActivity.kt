package com.nldining.app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nldining.app.models.Review
import com.nldining.app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewActivity : AppCompatActivity() {

    private var restaurantId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_activity) // pas aan naar jouw layout-bestand

        // Haal restaurantId uit intent extras
        restaurantId = intent.getIntExtra("restaurantId", -1)
        if (restaurantId == -1) {
            Toast.makeText(this, "Restaurant ID ontbreekt", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val seekFood = findViewById<SeekBar>(R.id.seekFood)
        val seekService = findViewById<SeekBar>(R.id.seekService)
        val seekAmbiance = findViewById<SeekBar>(R.id.seekDecor)
        val editTextReview = findViewById<EditText>(R.id.editTextReview)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmitReview)

        buttonSubmit.setOnClickListener {
            val foodScore = seekFood.progress
            val serviceScore = seekService.progress
            val ambianceScore = seekAmbiance.progress
            val reviewText = editTextReview.text.toString().trim()

            if (reviewText.isEmpty()) {
                Toast.makeText(this, "Schrijf alsjeblieft een review", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val reviewRequest = Review(
                reviewScoreFood = foodScore,
                reviewScoreService = serviceScore,
                reviewScoreAmbiance = ambianceScore,
                lastReview = reviewText
            )

            RetrofitClient.NLDiningAPI.submitReview(restaurantId, reviewRequest)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@ReviewActivity, "Review succesvol verstuurd", Toast.LENGTH_SHORT).show()
                            finish() // sluit deze activity, gaat terug naar lijst/detail
                        } else {
                            Toast.makeText(this@ReviewActivity, "Fout bij versturen review", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@ReviewActivity, "Netwerkfout: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}
