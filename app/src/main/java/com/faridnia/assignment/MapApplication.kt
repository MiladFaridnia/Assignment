package com.faridnia.assignment

import android.app.Application
import com.faridnia.assignment.di.apiModule
import com.faridnia.assignment.di.repositoryModule
import com.faridnia.assignment.di.retrofitModule
import com.faridnia.assignment.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MapApplication : Application() {

    override fun onCreate() {

        super.onCreate()

        startKoin {

            androidLogger()

            androidContext(this@MapApplication)

            modules(listOf(repositoryModule, viewModelModule, retrofitModule, apiModule))

        }

    }
}