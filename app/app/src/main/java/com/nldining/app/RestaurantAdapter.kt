package com.nldining.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nldining.app.models.Restaurant
import com.nldining.app.network.RetrofitClient
import kotlinx.coroutines.*
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import java.util.Locale


class RestaurantAdapter(
    private var restaurants: List<Restaurant>,
    private val onSelectRestaurant: (Restaurant, Boolean) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>(), CoroutineScope by MainScope() {

    private var selectedItems = mutableSetOf<Restaurant>()

    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBoxSelect)
        val nameTextView: TextView = itemView.findViewById(R.id.textViewName) // voorbeeld
        val categoryTextview: TextView = itemView.findViewById(R.id.textViewCategory)
        val scoreTextview: TextView = itemView.findViewById(R.id.textViewScore)

        fun bind(restaurant: Restaurant) {
            nameTextView.text = restaurant.naam
            categoryTextview.text = restaurant.categorie
            scoreTextview.text = String.format(Locale.getDefault(), "Score: %.1f", restaurant.reviewScoreOverall)

            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = selectedItems.any { it.id == restaurant.id }

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked && restaurant.latitude == null) {
                    launch {
                        delay(1000)
                        try {
                            val responseList = withContext(Dispatchers.IO) {
                                RetrofitClient.NominatimApi.searchLocation(restaurant.adres)
                            }
                            val firstMatch = responseList.firstOrNull()

                            val updatedRestaurant = if (firstMatch != null) {
                                restaurant.copy(
                                    latitude = firstMatch.lat.toDoubleOrNull(),
                                    longitude = firstMatch.lon.toDoubleOrNull()
                                )
                            } else restaurant

                            selectedItems.removeAll { it.id == updatedRestaurant.id }
                            selectedItems.add(updatedRestaurant)
                            notifyItemChanged(adapterPosition)

                            onSelectRestaurant(updatedRestaurant, true)

                        } catch (e: Exception) {
                            checkBox.isChecked = false
                            onSelectRestaurant(restaurant, false)
                            Log.e("API Communication", "The communication with Nominatim went wrong")
                        }
                    }
                } else {
                    selectedItems.removeAll { it.id == restaurant.id }
                    onSelectRestaurant(restaurant, false)
                }
            }

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, RestaurantDetailActivity::class.java).apply {
                    putExtra("restaurant", restaurant)
                }
                context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount() = restaurants.size

    fun updateData(newRestaurants: List<Restaurant>) {
        val diffCallback = RestaurantDiffCallback(restaurants, newRestaurants)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        restaurants = newRestaurants
        diffResult.dispatchUpdatesTo(this)
    }

    fun updateSelection(newSelection: Set<Restaurant>) {
        val oldSelection = selectedItems.toSet()
        selectedItems = newSelection.toMutableSet()

        val changedItems = (oldSelection union newSelection) - (oldSelection intersect newSelection)
        changedItems.forEach { changedRestaurant ->
            val index = restaurants.indexOfFirst { it.id == changedRestaurant.id }
            if (index != -1) notifyItemChanged(index)
        }
    }

    class RestaurantDiffCallback(
        private val oldList: List<Restaurant>,
        private val newList: List<Restaurant>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
