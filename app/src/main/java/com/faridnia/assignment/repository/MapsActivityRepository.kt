package com.faridnia.assignment.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.faridnia.assignment.isNetworkAvailable
import com.faridnia.assignment.network.RetrofitClientInstance
import com.faridnia.assignment.network.VehicleService
import com.faridnia.assignment.network.model.Vehicle
import com.faridnia.assignment.network.model.Vehicles
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivityRepository {

    val showProgress = MutableLiveData<Boolean>()
    val vehicles = MutableLiveData<List<Vehicle>>()

    fun changeProgressState() {
        showProgress.value = !(showProgress.value != null && showProgress.value!!)
    }

    fun fetchVehicles(context: Context?) {
        if (isNetworkAvailable(context)) {
            fetchDataFromServer()
        } else {
            fetchDataFromDatabase()
        }
    }

    private fun fetchDataFromDatabase() {

    }

    private fun fetchDataFromServer() {
        showProgress.value = true

        val service = RetrofitClientInstance.retrofitInstance?.create(VehicleService::class.java)
        val call = service?.getVehicles()

        call?.enqueue(object : Callback<Vehicles> {

            override fun onFailure(call: Call<Vehicles>, t: Throwable) {
                showProgress.value = false
            }

            override fun onResponse(
                call: Call<Vehicles>, response: Response<Vehicles>
            ) {
                showProgress.value = false
                vehicles.value = response.body()?.vehicles
            }
        })
    }
}