package com.nldining.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.activityViewModels

class RestaurantListFragment : Fragment() {

    private lateinit var restaurantAdapter: RestaurantAdapter

    // ViewModel gedeeld met andere fragmenten
    private val sharedViewModel: SharedViewModel by activityViewModels {
        SharedViewModelFactory(NominatimService.create())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewRestaurants)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Adapter opzetten, gebruikt ViewModel functie voor selectie
        restaurantAdapter = RestaurantAdapter(
            requireContext(),
            mutableListOf() // wordt straks vervangen zodra LiveData komt
        ) { restaurant, isChecked ->
            sharedViewModel.toggleRestaurantSelection(restaurant, isChecked)
        }

        recyclerView.adapter = restaurantAdapter

        // Observeer de restaurantlijst vanuit ViewModel
        sharedViewModel.restaurantList.observe(viewLifecycleOwner) { updatedList ->
            restaurantAdapter.updateData(updatedList)
        }

        return view
    }
}