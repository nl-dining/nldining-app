package com.nldining.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nldining.app.models.Restaurant
import kotlinx.coroutines.*
import android.content.Intent
import com.nldining.app.network.RetrofitClient


class RestaurantAdapter(
    private var restaurants: List<Restaurant>,
    private val onSelectRestaurant: (Restaurant, Boolean) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>(), CoroutineScope by MainScope() {

    private var selectedItems = mutableSetOf<Restaurant>()

    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBoxSelect)
        val nameTextView: TextView = itemView.findViewById(R.id.textViewName) // voorbeeld

        fun bind(restaurant: Restaurant) {
            nameTextView.text = restaurant.naam

            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = selectedItems.any { it.id == restaurant.id }

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    launch {
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
        restaurants = newRestaurants
        notifyDataSetChanged()
    }

    fun updateSelection(newSelection: Set<Restaurant>) {
        selectedItems = newSelection.toMutableSet()
        notifyDataSetChanged()
    }

}
