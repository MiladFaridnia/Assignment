package com.faridnia.assignment.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.faridnia.assignment.repository.MapsActivityRepository

class MapsActivityViewModel : AndroidViewModel(Application()) {

    private val repository= MapsActivityRepository()
    val showProgress : LiveData<Boolean>

    init {
        this.showProgress = repository.showProgress
    }

    fun changeProgressState(){
        repository.changeProgressState()
    }
}