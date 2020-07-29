package com.faridnia.assignment.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.faridnia.assignment.room.Vehicle
import com.faridnia.assignment.room.VehicleDatabase
import com.faridnia.assignment.repository.VehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsActivityViewModel(application : Application) : AndroidViewModel(application) {

    private val vehicleRepository : VehicleRepository
    val showProgress: LiveData<Boolean>
    internal val vehicles: LiveData<List<Vehicle>>

    init {
        val vehicleDatabase = VehicleDatabase.getDatabase(application,viewModelScope)
        val vehicleDao =  vehicleDatabase.vehicleDao()
        vehicleRepository =
            VehicleRepository(vehicleDao)
        this.vehicles = vehicleDao.getAll()
        this.showProgress = vehicleRepository.showProgress
    }

    fun changeProgressState() {
        vehicleRepository.changeProgressState()
    }

    fun fetchVehicles(){
        vehicleRepository.fetchVehicles()
    }

    fun insert(vehicle: Vehicle) = viewModelScope.launch(Dispatchers.IO) {
        vehicleRepository.insert(vehicle)
    }
}