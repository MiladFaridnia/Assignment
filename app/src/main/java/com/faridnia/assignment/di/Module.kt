package com.faridnia.assignment.di

import androidx.room.Room
import com.faridnia.assignment.network.RetrofitClientInstance.BASE_URL
import com.faridnia.assignment.network.VehicleService
import com.faridnia.assignment.repository.VehicleRepository
import com.faridnia.assignment.room.VehicleDatabase
import com.faridnia.assignment.viewModel.MapsActivityViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {

    viewModel {
        MapsActivityViewModel(get())
    }
}

val repositoryModule = module {

    single {
        VehicleRepository(get())
    }

    single {
        Room.databaseBuilder(androidContext(), VehicleDatabase::class.java, "weather-db")
            .build()
    }

    single { get<VehicleDatabase>().vehicleDao() }
}

val apiModule = module {

    fun provideApi(retrofit: Retrofit): VehicleService {
        return retrofit.create(VehicleService::class.java)
    }

    single { provideApi(get()) }
}

val retrofitModule = module {

    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    fun provideRetrofit(factory: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .build()
    }

    single { provideGson() }

    single { provideRetrofit(get()) }
}