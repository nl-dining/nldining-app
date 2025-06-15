package com.nldining.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.activityViewModels
import kotlin.compareTo


class RestaurantListFragment : Fragment() {

    private lateinit var restaurantAdapter: RestaurantAdapter

    // Gedeeld ViewModel voor communicatie met andere fragmenten
    private val sharedViewModel: SharedViewModel by activityViewModels {
        SharedViewModelFactory(NominatimService.create())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_restaurant_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewRestaurants)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Adapter maken (zonder eigen lijst, dat regelt ListAdapter intern)
        restaurantAdapter = RestaurantAdapter(requireContext()) { restaurant, isChecked ->
            val currentList = sharedViewModel.restaurantList.value ?: return@RestaurantAdapter

            val selectedCount = currentList.count { it.isSelected }

            if (isChecked && selectedCount >= 6) {
                Toast.makeText(requireContext(), "Je kunt maximaal 5 restaurants selecteren.", Toast.LENGTH_SHORT).show()
                // Checkbox wordt niet geüpdatet hier – dit gebeurt via observer na LiveData update
                return@RestaurantAdapter
            }

            sharedViewModel.toggleRestaurantSelection(restaurant, isChecked)
        }

        recyclerView.adapter = restaurantAdapter

        // Observeer de restaurantlijst vanuit ViewModel en update adapter met DiffUtil
        sharedViewModel.restaurantList.observe(viewLifecycleOwner) { updatedList ->
            restaurantAdapter.submitList(updatedList.toList()) // maak nieuwe kopie om DiffUtil te activeren
        }

        return view
    }
}