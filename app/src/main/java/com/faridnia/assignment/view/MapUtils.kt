package com.faridnia.assignment.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.faridnia.assignment.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlinx.android.synthetic.main.marker_layout.view.*

class MapUtils {

    fun getMarkerIcon(context: Context, url: String, listener: (BitmapDescriptor) -> Unit) {
        val markerView = View.inflate(context, R.layout.marker_layout, null)
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : SimpleTarget<Bitmap>() {

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    markerView.marker.setImageBitmap(resource)
                    listener.invoke(BitmapDescriptorFactory.fromBitmap(getBitmapFromView(markerView)))
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

}