package com.nldining.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: RestaurantAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_restaurant_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewRestaurants)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = RestaurantAdapter(emptyList()) { restaurant, isSelected ->
            viewModel.updateSelection(restaurant, isSelected)
        }
        recyclerView.adapter = adapter

        viewModel.restaurants.observe(viewLifecycleOwner) { restaurants ->
            adapter.updateData(restaurants)
        }

        viewModel.selectedRestaurants.observe(viewLifecycleOwner) { selected ->
            adapter.updateSelection(selected)
        }
    }
}

