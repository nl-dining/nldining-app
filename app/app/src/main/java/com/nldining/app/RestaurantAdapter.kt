package com.nldining.app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import kotlin.apply
//import kotlin.jvm.java

class RestaurantAdapter(
    private val context: Context,
    private val restaurants: MutableList<Restaurant>,
    private val onCheckedChange: (Restaurant, Boolean) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameText)
        val addressTextView: TextView = itemView.findViewById(R.id.addressText)
        val markerCheckBox: CheckBox = itemView.findViewById(R.id.checkbox)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val restaurant = restaurants[position]
                    val intent = Intent(context, RestaurantDetailActivity::class.java).apply {
                        putExtra("name", restaurant.name)
                        putExtra("address", restaurant.address)
                        putExtra("category", restaurant.category)
                        putExtra("tags", restaurant.tags)
                        putExtra("reviewScoreOverall", restaurant.reviewScoreOverall)
                        putExtra("reviewScoreFood", restaurant.reviewScoreFood)
                        putExtra("reviewScoreService", restaurant.reviewScoreService)
                        putExtra("reviewScoreAmbiance", restaurant.reviewScoreAmbiance)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.nameTextView.text = restaurant.name
        holder.addressTextView.text = restaurant.address
        holder.markerCheckBox.isChecked = restaurant.isSelected

        holder.markerCheckBox.setOnCheckedChangeListener(null)
        holder.markerCheckBox.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChange(restaurant, isChecked)
        }
    }

    override fun getItemCount(): Int = restaurants.size

    // Nieuwe functie om lijst te updaten vanuit ViewModel
    fun updateData(newList: List<Restaurant>) {
        restaurants.clear()
        restaurants.addAll(newList)
        notifyDataSetChanged()
    }
}