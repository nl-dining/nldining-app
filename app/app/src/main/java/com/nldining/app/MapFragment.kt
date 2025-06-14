package com.nldining.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.fragment.app.activityViewModels
import kotlin.also
import kotlin.collections.filter
import kotlin.collections.forEach

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private val viewModel: SharedViewModel by activityViewModels {
        SharedViewModelFactory(NominatimService.create())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment
            ?: SupportMapFragment.newInstance().also {
                childFragmentManager.beginTransaction().replace(R.id.mapFragment, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Zet de standaardpositie op Nederland
        val nederland = LatLng(52.1326, 5.2913) // Midden van Nederland
        val zoomLevel = 7.5f

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(nederland, zoomLevel))
        viewModel.restaurantList.observe(viewLifecycleOwner) { list ->
            map.clear()
            list.filter { it.isSelected && it.lat != null && it.lon != null }.forEach {
                val pos = LatLng(it.lat!!, it.lon!!)
                map.addMarker(MarkerOptions().position(pos).title(it.name).snippet(it.address))
            }
        }
    }
}