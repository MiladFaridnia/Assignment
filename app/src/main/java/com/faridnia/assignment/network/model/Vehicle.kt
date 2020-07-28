package com.faridnia.assignment.network.model


import com.google.gson.annotations.SerializedName

data class Vehicle(
    val bearing: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    val lat: Double,
    val lng: Double,
    val type: String
)