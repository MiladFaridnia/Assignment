package com.faridnia.assignment.view

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.faridnia.assignment.R
import com.faridnia.assignment.room.Vehicle
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottomsheet.view.*

class BottomSheet(private val context: Context,
                  private val items: List<Vehicle>) {

    private val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(context)

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.bottomsheet, null)
        bottomSheetDialog.setContentView(view)

        with(view) {
            recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerview.adapter = BottomSheetAdapter(items)
        }
    }

    fun show() {
        bottomSheetDialog.show()
    }
}