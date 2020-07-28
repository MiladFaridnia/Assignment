package com.faridnia.assignment.network

import com.faridnia.assignment.network.model.Vehicles
import retrofit2.Call
import retrofit2.http.GET

const val BASE_URL = "https://snapp.ir/assets/test/"

interface VehicleService {

    @GET("document.json")
    fun getVehicles(): Call<Vehicles>
}