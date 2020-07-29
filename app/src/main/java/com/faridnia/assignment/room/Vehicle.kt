package com.faridnia.assignment.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "vehicle_table")
data class Vehicle(
    @field:PrimaryKey(autoGenerate = true) val vehicleId: Int,
    @field:ColumnInfo(name = "bearing") val bearing: Int?,
    @SerializedName("image_url")
    @field:ColumnInfo(name = "imageUrl")
    val imageUrl: String?,
    @field:ColumnInfo(name = "lat") val lat: Double?,
    @field:ColumnInfo(name = "lng") val lng: Double?,
    @field:ColumnInfo(name = "type") val type: String?
)
