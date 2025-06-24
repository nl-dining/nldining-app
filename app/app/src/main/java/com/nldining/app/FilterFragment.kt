package com.nldining.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nldining.app.models.FilterData
import com.nldining.app.models.enums.Category
import com.nldining.app.models.enums.City
import com.nldining.app.models.enums.Tags

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

        val spinnerCity = view.findViewById<Spinner>(R.id.spinnerPlaats)
        val spinnerCategory = view.findViewById<Spinner>(R.id.spinnerCategory)
        val spinnerTags = view.findViewById<Spinner>(R.id.spinnerTags)

        val seekBarScoreOverall = view.findViewById<SeekBar>(R.id.seekBarScoreOverall)
        val seekBarScoreFood = view.findViewById<SeekBar>(R.id.seekBarScoreFood)
        val seekBarScoreService = view.findViewById<SeekBar>(R.id.seekBarScoreService)
        val seekBarScoreAmbiance = view.findViewById<SeekBar>(R.id.seekBarScoreAmbiance)
        val buttonSearch = view.findViewById<Button>(R.id.buttonSearch)

        val textScoreOverall = view.findViewById<TextView>(R.id.textScoreOverall)
        val textScoreFood = view.findViewById<TextView>(R.id.textScoreFood)
        val textScoreService = view.findViewById<TextView>(R.id.textScoreService)
        val textScoreAmbiance = view.findViewById<TextView>(R.id.textScoreAmbiance)

        fun updateScoreText(seekBar: SeekBar, textView: TextView) {
            textView.text = "Score: ${seekBar.progress / 10.0}"
        }

        seekBarScoreOverall.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateScoreText(seekBar!!, textScoreOverall)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBarScoreFood.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateScoreText(seekBar!!, textScoreFood)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBarScoreService.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateScoreText(seekBar!!, textScoreService)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBarScoreAmbiance.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateScoreText(seekBar!!, textScoreAmbiance)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        // Spinner-opties
        val cityOptions = City.values().map { it.displayName }
        val categoryOptions = Category.values().map { it.displayName }
        val tagOptions = Tags.values().map { it.displayName }
        spinnerTags.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, tagOptions)
        spinnerCity.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, cityOptions)
        spinnerCategory.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categoryOptions)

        buttonSearch.setOnClickListener {
            var selectedCity = spinnerCity.selectedItem.toString()
            var selectedCategory = spinnerCategory.selectedItem.toString();
            var selectedTags = spinnerTags.selectedItem.toString();

            if(selectedCity == "Geen voorkeur"){
                selectedCity = "";
            }

            if(selectedCategory == "Geen voorkeur"){
                selectedCategory = "";
            }

            if(selectedTags == "Geen voorkeur"){
                selectedTags = "";
            }

            val filter = FilterData(
                city = selectedCity,
                category = selectedCategory,
                tags = selectedTags,
                scoreOverall = seekBarScoreOverall.progress / 10.0,
                scoreFood = seekBarScoreFood.progress / 10.0,
                scoreService = seekBarScoreService.progress / 10.0,
                scoreAmbiance = seekBarScoreAmbiance.progress / 10.0
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
