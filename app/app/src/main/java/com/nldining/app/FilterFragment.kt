package com.nldining.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nldining.app.models.FilterData

class FilterFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        val editTextCity = view.findViewById<EditText>(R.id.editTextCity)
        val editTextCategory = view.findViewById<EditText>(R.id.editTextCategory)
        val editTextTags = view.findViewById<EditText>(R.id.editTextTags)
        val editTextScoreOverall = view.findViewById<EditText>(R.id.editTextScoreOverall)
        val editTextScoreFood = view.findViewById<EditText>(R.id.editTextScoreFood)
        val editTextScoreService = view.findViewById<EditText>(R.id.editTextScoreService)
        val editTextScoreAmbiance = view.findViewById<EditText>(R.id.editTextScoreAmbiance)
        val buttonSearch = view.findViewById<Button>(R.id.buttonSearch)

        buttonSearch.setOnClickListener {
            val filter = FilterData(
                city = editTextCity.text.toString().trim(),
                category = editTextCategory.text.toString().trim(),
                tags = editTextTags.text.toString().trim(),
                scoreOverall = editTextScoreOverall.text.toString().toDoubleOrNull(),
                scoreFood = editTextScoreFood.text.toString().toDoubleOrNull(),
                scoreService = editTextScoreService.text.toString().toDoubleOrNull(),
                scoreAmbiance = editTextScoreAmbiance.text.toString().toDoubleOrNull()
            )

            viewModel.setFilter(filter)

            Toast.makeText(requireContext(), "Filters toegepast", Toast.LENGTH_SHORT).show()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ListFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}

