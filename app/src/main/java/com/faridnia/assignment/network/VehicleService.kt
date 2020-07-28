package com.faridnia.assignment.network

import com.faridnia.assignment.network.model.Vehicles
import retrofit2.Call
import retrofit2.http.GET


interface VehicleService {

    @GET("document.json")
    fun getVehicles(): Call<Vehicles>
}