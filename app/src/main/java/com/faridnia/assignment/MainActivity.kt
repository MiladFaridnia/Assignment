package com.faridnia.assignment


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        setupKoinFragmentFactory()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_maps)

        initMapFragment()
    }

    private fun initMapFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, MapsFragment::class.java, null)
            .commit()
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

}
