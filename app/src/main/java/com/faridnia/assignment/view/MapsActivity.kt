package com.faridnia.assignment.view

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.faridnia.assignment.R
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

        viewModel.changeProgressState()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val tehran = LatLng(35.6, 51.3)
        mMap.addMarker(MarkerOptions().position(tehran).title("Marker in Tehran"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tehran))
    }

    companion object {
        private val TAG = MapsActivity::class.java.simpleName
        private const val DEFAULT_ZOOM = 15
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }

}
