package com.faridnia.assignment


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_maps)

        initMapFragment()
    }

    private fun initMapFragment() {
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, MapsFragment.newInstance())
            .commit()
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

}
