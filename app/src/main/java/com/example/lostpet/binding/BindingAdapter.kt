package com.example.lostpet.binding

import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.example.lostpet.itemAdapter.SpinnerAdapter
import com.example.lostpet.model.Gender
import pl.aprilapps.easyphotopicker.MediaFile
import java.io.File

@BindingAdapter("entriesType")
fun Spinner.setTypeEntries(entries: List<Gender>?) {
    if (entries != null) {
        val arrayAdapter = object :
            SpinnerAdapter<Gender?>(context, android.R.layout.simple_spinner_item, entries) {
            override fun getItemId(position: Int): Long {
                return entries[position].genderId.toLong()
            }
        }
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter = arrayAdapter
    }

    @BindingAdapter("srcBitmap")
    fun ImageView.setImageBitmap(entries: File?) {
        val imageUrl = Uri.parse(entries?.absolutePath)
        setImageURI(imageUrl)
    }
}
