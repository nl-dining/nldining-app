package com.nldining.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nldining.app.models.Restaurant

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var viewModel: MainViewModel
    private var googleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        viewModel.selectedRestaurants.observe(viewLifecycleOwner) { selectedRestaurants ->
            Log.d("MapFragment", "Aantal geselecteerde restaurants om te tonen: ${selectedRestaurants.size}")
            updateMarkers(selectedRestaurants.toList())
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val netherlands = LatLng(52.1326, 5.2913)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(netherlands, 7.5f))

        viewModel.selectedRestaurants.value?.let {
            updateMarkers(it.toList())
        }
    }

    private fun updateMarkers(restaurants: List<Restaurant>) {
        googleMap?.let { map ->
            map.clear()

            if (restaurants.isEmpty()) {
                val netherlands = LatLng(52.1326, 5.2913)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(netherlands, 7.5f))
            } else {
                restaurants.forEach { restaurant ->
                    val lat = restaurant.latitude
                    val lon = restaurant.longitude
                    if (lat != null && lon != null) {
                        val position = LatLng(lat, lon)
                        Log.d("MapFragment", "Marker toevoegen: ${restaurant.naam} op $lat, $lon")
                        map.addMarker(
                            MarkerOptions()
                                .position(position)
                                .title(restaurant.naam)
                                .snippet(restaurant.adres)
                        )
                    }
                }
                val first = restaurants.firstOrNull()
                if (first?.latitude != null && first.longitude != null) {
                    val pos = LatLng(first.latitude, first.longitude)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 12f))
                }
            }
        }
    }
}
