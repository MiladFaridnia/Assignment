package com.faridnia.assignment.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.faridnia.assignment.network.RetrofitClientInstance
import com.faridnia.assignment.network.VehicleService
import com.faridnia.assignment.room.Vehicles
import com.faridnia.assignment.room.Vehicle
import com.faridnia.assignment.room.VehicleDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehicleRepository(private val vehicleDao: VehicleDao) {

    val vehicles = MutableLiveData<List<Vehicle>>()
    val showProgress = MutableLiveData<Boolean>()

    fun changeProgressState() {
        showProgress.value = !(showProgress.value != null && showProgress.value!!)
    }

    suspend fun insert(vehicle: Vehicle) {
        vehicleDao.insert(vehicle)
    }

    suspend fun deleteAll() {
        vehicleDao.deleteAll()
    }

    fun fetchVehicles() {
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
                insertToDB()
            }

            private fun insertToDB() {
                GlobalScope.launch {
                    deleteAll() //Because cars does not have ids from server
                    vehicles.value?.forEach {
                        insert(it)
                    }
                }
            }
        })
    }
}