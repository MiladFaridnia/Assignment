package com.faridnia.assignment.view


import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.faridnia.assignment.R
import com.faridnia.assignment.isNetworkAvailable
import com.faridnia.assignment.room.Vehicle
import com.faridnia.assignment.viewModel.MapsActivityViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application))
            .get(MapsActivityViewModel::class.java)

        if (isNetworkAvailable(this)) {
            if (viewModel.vehicles.value == null) {
                viewModel.fetchVehicles()
            }
        }

        viewModel.showProgress.observe(this, Observer {
            if (it) {
                progress.visibility = VISIBLE
            } else {
                progress.visibility = GONE
            }
        })

        viewModel.vehicles.observe(this, Observer { list ->
            if (list!= null && list.isNotEmpty() && isMapReady) {
                if (isNetworkAvailable(this)) {
                    list.forEach { vehicle ->
                        placeMarkerOnMap(vehicle)
                    }
                    mMap.animateCamera(getBetterZoom(list.map { LatLng(it.lat!!, it.lng!!) }))
                } else {
                    showVehicleListBottomsheet(list)
                    list.forEach {
                        Log.d("Milad", "items offline: " + it.imageUrl)
                    }
                }
            }
        })
    }

    private fun showVehicleListBottomsheet(
        vehicles: List<Vehicle>?
    ) {
        if (vehicles!=null) {
            BottomSheet(this, vehicles).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        isMapReady = true
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(TEHRAN, DEFAULT_ZOOM))
    }

    private fun placeMarkerOnMap(vehicle: Vehicle) {
        if (vehicle.lat != null && vehicle.lng != null
            && vehicle.imageUrl != null && vehicle.bearing != null
        ) {
            val vehicleLatLng = LatLng(vehicle.lat, vehicle.lng)
            getMarkerIcon(this, vehicle.imageUrl, vehicle.bearing) {
                val options = MarkerOptions()
                    .position(vehicleLatLng)
                    .icon(it)
                mMap.addMarker(options)
            }
        }
    }

    companion object {
        private val TAG = MapsActivity::class.java.simpleName
        private val TEHRAN = LatLng(35.6, 51.3)
        private const val DEFAULT_ZOOM = 11F
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }

}
