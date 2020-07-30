package com.faridnia.assignment

import android.app.Application
import com.faridnia.assignment.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class MapApplication : Application() {

    override fun onCreate() {

        super.onCreate()

        startKoin {

            androidLogger()

            androidContext(this@MapApplication)

            fragmentFactory()

            modules(listOf(repositoryModule, viewModelModule, retrofitModule, apiModule, fragmentModules))

        }

    }
}