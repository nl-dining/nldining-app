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

            // Probeer selectie te togglen via ViewModel
            val success = sharedViewModel.toggleRestaurantSelection(restaurant, isChecked)

            if (!success) {
                // Teveel geselecteerd, toon waarschuwing
                Toast.makeText(requireContext(), "Je kunt maximaal 5 restaurants selecteren.", Toast.LENGTH_SHORT).show()

                // Zet checkbox terug naar vorige staat
                // Zet lokale kopie terug zodat DiffUtil een echte verandering ziet
                val revertedList = currentList.map {
                    if (it == restaurant) it.copy(isSelected = false) else it
                }

                sharedViewModel.forceUpdateList(revertedList)
                restaurantAdapter.notifyItemChanged(currentList.indexOf(restaurant))
            }
        }

        recyclerView.adapter = restaurantAdapter

        // Observeer de restaurantlijst vanuit ViewModel en update adapter met DiffUtil
        sharedViewModel.restaurantList.observe(viewLifecycleOwner) { updatedList ->
            restaurantAdapter.submitList(updatedList.toList()) // maak nieuwe kopie om DiffUtil te activeren
        }

        return view
    }
}