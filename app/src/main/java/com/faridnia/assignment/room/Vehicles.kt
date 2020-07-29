package com.faridnia.assignment.room

import com.google.gson.annotations.SerializedName

data class Vehicles (
    @SerializedName("vehicles") val vehicles : List<Vehicle>
)
