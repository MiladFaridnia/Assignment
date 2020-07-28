package com.faridnia.assignment.view


import android.location.Location
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.faridnia.assignment.R
import com.faridnia.assignment.network.model.Vehicle
import com.faridnia.assignment.viewModel.MapsActivityViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var isMapReady: Boolean = false
    private lateinit var mMap: GoogleMap
    private var cameraPosition: CameraPosition? = null
    private var lastKnownLocation: Location? = null
    private lateinit var viewModel: MapsActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }

        setContentView(R.layout.activity_maps)

        isMapReady = false

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel = ViewModelProvider(this).get(MapsActivityViewModel::class.java)

        viewModel.showProgress.observe(this, Observer {
            if (it) {
                progress.visibility = VISIBLE
            } else {
                progress.visibility = GONE
            }
        })

        if (viewModel.vehicles.value == null) {
            viewModel.fetchVehicles()
        }

        viewModel.vehicles.observe(this, Observer {
            if (it.isNotEmpty() && isMapReady) {
                it.forEach { vehicle ->
                    placeMarkerOnMap(vehicle)
                }
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        isMapReady = true
        val tehran = LatLng(35.6, 51.3)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tehran))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tehran, DEFAULT_ZOOM))
    }

    private fun placeMarkerOnMap(vehicle: Vehicle) {
        val vehicleLatLng = LatLng(vehicle.lat, vehicle.lng)
        MapUtils().getMarkerIcon(this, vehicle.imageUrl) {
            val options = MarkerOptions()
                .position(vehicleLatLng)
                .icon(it)
            mMap.addMarker(options)
        }
    }

    companion object {
        private val TAG = MapsActivity::class.java.simpleName
        private const val DEFAULT_ZOOM = 11F
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }

}
