package com.faridnia.assignment.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicle_table")
            /*  No need to add suspend keyword because it will automatically handled asynchronous.
              Since i'm returning a LiveData object, there’s no need to use suspend
               on this method. In fact, Room won’t even allow it. */
    fun getAll(): LiveData<List<Vehicle>>

    @Query("SELECT * FROM vehicle_table WHERE vehicleId IN (:vehicleIds)")
    suspend fun loadAllByIds(vehicleIds: IntArray): List<Vehicle>

    @Query("SELECT * FROM vehicle_table WHERE vehicleId  =:id LIMIT 1")
    suspend fun findById(id: Int): Vehicle

    @Insert
    suspend fun insertAll(vararg vehicles: Vehicle)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vehicle: Vehicle)

    @Delete
    suspend fun delete(vehicle: Vehicle)

    @Query("DELETE FROM vehicle_table")
    suspend fun deleteAll()
}