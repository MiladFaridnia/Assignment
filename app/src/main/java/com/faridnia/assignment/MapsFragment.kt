package com.faridnia.assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.faridnia.assignment.room.Vehicle
import com.faridnia.assignment.view.BottomSheet
import com.faridnia.assignment.view.getBetterZoom
import com.faridnia.assignment.view.getMarkerIcon
import com.faridnia.assignment.viewModel.MapsActivityViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.*
import kotlinx.android.synthetic.main.fragment_maps.view.*

class MapsFragment : Fragment() , OnMapReadyCallback {

    private var isMapReady: Boolean = false
    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: MapsActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_maps, container, false)

        isMapReady = false

        getMapFragment()

        initViewModel()

        fetchData()

        observeProgressbarState()

        observeVehicles()

        setonClickListenerForRefreshButton(rootView)

        return rootView
    }

    private fun setonClickListenerForRefreshButton(rootView: View) {
        rootView.refresh.setOnClickListener {
            fetchData()
        }
    }

    private fun getMapFragment() {
        val mapFragment =
            childFragmentManager.fragments[0] as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity?.application!!)
        )
            .get(MapsActivityViewModel::class.java)
    }

    private fun fetchData() {
        if (isNetworkAvailable(activity)) {
            viewModel.fetchVehicles()
        } else {
            Toast.makeText(context, "Network Unavailable", Toast.LENGTH_LONG).show()
        }
    }

    private fun observeVehicles() {
        viewModel.vehicles.observe(this, Observer { list ->
            if (list != null && list.isNotEmpty() && isMapReady) {
                if (isNetworkAvailable(activity)) {
                    list.forEach { vehicle ->
                        placeMarkerOnMap(vehicle)
                    }
                    mMap.animateCamera(
                        getBetterZoom(list.map { LatLng(it.lat!!, it.lng!!) })
                    )
                } else {
                    showVehicleListBottomSheet(list)
                }
            }
        })
    }

    private fun observeProgressbarState() {
        viewModel.showProgress.observe(this, Observer {
            if (it) {
                progress.visibility = View.VISIBLE
            } else {
                progress.visibility = View.GONE
            }
        })
    }

    private fun showVehicleListBottomSheet(vehicles: List<Vehicle>?) {
        if (vehicles != null) {
            BottomSheet(activity as FragmentActivity, vehicles).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        isMapReady = true
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                TEHRAN,
                DEFAULT_ZOOM
            ))
    }

    private fun placeMarkerOnMap(vehicle: Vehicle) {
        if (vehicle.lat != null && vehicle.lng != null
            && vehicle.imageUrl != null && vehicle.bearing != null
        ) {
            val vehicleLatLng = LatLng(vehicle.lat, vehicle.lng)
            getMarkerIcon(activity?.baseContext!!, vehicle.imageUrl, vehicle.bearing) {
                val options = MarkerOptions()
                    .position(vehicleLatLng)
                    .icon(it)
                mMap.addMarker(options)
            }
        }
    }

    companion object {
        private val TEHRAN = LatLng(35.6, 51.3)
        private const val DEFAULT_ZOOM = 11F

        @JvmStatic
        fun newInstance() = MapsFragment()
    }
}
