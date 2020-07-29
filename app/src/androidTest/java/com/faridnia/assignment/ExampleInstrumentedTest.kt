package com.faridnia.assignment

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.faridnia.assignment.network.RetrofitClientInstance
import com.faridnia.assignment.network.VehicleService

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.faridnia.assignment", appContext.packageName)
    }

    @Test
    fun canGetVehiclesMethod () {
        // call the api
        val service = RetrofitClientInstance.retrofitInstance?.create(VehicleService::class.java)
        val response = service?.getVehicles()?.execute()
        // verify the response is OK
        assert(response?.code()?.equals(200)!!)
    }
}
