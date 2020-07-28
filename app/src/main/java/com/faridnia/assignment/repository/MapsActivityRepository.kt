package com.faridnia.assignment.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.faridnia.assignment.network.BASE_URL
import com.faridnia.assignment.network.VehicleService
import com.faridnia.assignment.network.model.Vehicles
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapsActivityRepository {

    val showProgress = MutableLiveData<Boolean>()

    fun changeProgressState() {
        showProgress.value = !(showProgress.value != null && showProgress.value!!)
    }

    fun fetchVehicles() {
        showProgress.value = true

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(VehicleService::class.java)
        service.getVehicles().enqueue(object : Callback<Vehicles> {

            override fun onFailure(call: Call<Vehicles>, t: Throwable) {
                showProgress.value = false
            }

            override fun onResponse(call: Call<Vehicles>, response: Response<Vehicles>
            ) {
                showProgress.value = false
                val vehicles = response.body()?.vehicles
                Log.d("Milad", "Response: ${Gson().toJson(vehicles)}")
            }
        })
    }
}