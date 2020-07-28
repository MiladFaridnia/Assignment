package com.faridnia.assignment.repository

import androidx.lifecycle.MutableLiveData

class MapsActivityRepository {

    val showProgress = MutableLiveData<Boolean>()

    fun changeProgressState(){
        showProgress.value = !(showProgress.value !=null && showProgress.value!!)
    }
}