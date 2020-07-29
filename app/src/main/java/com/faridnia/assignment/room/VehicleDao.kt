package com.faridnia.assignment.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicle_table")
    fun getAll(): LiveData<List<Vehicle>>

    @Query("SELECT * FROM vehicle_table WHERE vehicleId IN (:vehicleIds)")
    fun loadAllByIds(vehicleIds: IntArray): List<Vehicle>

    @Query("SELECT * FROM vehicle_table WHERE vehicleId  =:id LIMIT 1")
    fun findById(id: Int): Vehicle

    @Insert
    suspend fun insertAll(vararg vehicles: Vehicle)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vehicle: Vehicle)

    @Delete
    suspend fun delete(vehicle: Vehicle)

    @Query("DELETE FROM vehicle_table")
    suspend fun deleteAll()
}