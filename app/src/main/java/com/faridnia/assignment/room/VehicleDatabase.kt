package com.faridnia.assignment.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Vehicle::class], version = 1)
abstract class VehicleDatabase : RoomDatabase() {

    abstract fun vehicleDao(): VehicleDao

    private class VehicleDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                   // val vehicleDao = database.vehicleDao()
                    //vehicleDao.deleteAll()
                    Log.d("Milad" , "database Opened")
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: VehicleDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): VehicleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VehicleDatabase::class.java,
                    "vehicle_database"
                )
                    .addCallback(VehicleDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
