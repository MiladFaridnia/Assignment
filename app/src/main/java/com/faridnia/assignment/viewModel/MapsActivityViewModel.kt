package com.faridnia.assignment.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.faridnia.assignment.network.model.Vehicle
import com.faridnia.assignment.repository.MapsActivityRepository

class MapsActivityViewModel : AndroidViewModel(Application()) {

    private val repository = MapsActivityRepository()
    val showProgress: LiveData<Boolean>
    var vehicles: LiveData<List<Vehicle>>

    init {
        this.showProgress = repository.showProgress
        this.vehicles = repository.vehicles
    }

    fun changeProgressState() {
        repository.changeProgressState()
    }

    fun fetchVehicles(){
        repository.fetchVehicles()
    }
}