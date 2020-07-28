package com.faridnia.assignment.network.model

import com.google.gson.annotations.SerializedName

data class Vehicles (
    @SerializedName("vehicles") val vehicles : List<Vehicle>
)
