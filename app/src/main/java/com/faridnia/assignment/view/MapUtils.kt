package com.faridnia.assignment.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.faridnia.assignment.R
import com.faridnia.assignment.network.model.Vehicle
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.android.synthetic.main.marker_layout.view.*

fun getBetterZoom(vehicles: List<Vehicle>): CameraUpdate {
    val builder = LatLngBounds.Builder()

    vehicles.forEach { vehicle ->
        builder.include(LatLng(vehicle.lat, vehicle.lng))
    }

    val bounds = builder.build()
    val paddingInPixels = 150
    return CameraUpdateFactory.newLatLngBounds(bounds, paddingInPixels)
}

fun getMarkerIcon(
    context: Context,
    url: String,
    bearing: Int,
    listener: (BitmapDescriptor) -> Unit
) {
    val markerView = View.inflate(context, R.layout.marker_layout, null)
    Glide.with(context)
        .asBitmap()
        .load(url)
        .into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                markerView.marker.setImageBitmap(resource)
                val rotatedBitmap = rotateBitmap(getBitmapFromView(markerView), bearing.toFloat())
                listener.invoke(BitmapDescriptorFactory.fromBitmap(rotatedBitmap))
            }
        })
}

private fun getBitmapFromView(view: View): Bitmap {
    view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    val bitmap =
        Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    view.draw(canvas)
    return bitmap
}

private fun rotateBitmap(source: Bitmap, angle: Float): Bitmap? {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(
        source,
        0,
        0,
        source.width,
        source.height,
        matrix,
        true
    )
}
