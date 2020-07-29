package com.faridnia.assignment.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faridnia.assignment.R
import com.faridnia.assignment.room.Vehicle
import kotlinx.android.synthetic.main.vehicle_list_item.view.*

class BottomSheetAdapter(private val items: List<Vehicle>) : RecyclerView.Adapter<BottomSheetAdapter.BottomSheetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetViewHolder {
        return BottomSheetViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.vehicle_list_item, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BottomSheetViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class BottomSheetViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: Vehicle) {
            with(view) {
                typeTextView.text = item.type
                latTextView.text = item.lat.toString()
                lanTextView.text = item.lat.toString()
            }
        }
    }



}