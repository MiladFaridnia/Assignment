package com.faridnia.assignment.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.faridnia.assignment.room.Vehicle
import com.faridnia.assignment.repository.VehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MapsActivityViewModel(application : Application) : AndroidViewModel(application) ,
    KoinComponent {

     private val vehicleRepository : VehicleRepository by inject()

    val showProgress: LiveData<Boolean>
    internal val vehicles: LiveData<List<Vehicle>>

    init {
        this.vehicles = vehicleRepository.getAll()
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