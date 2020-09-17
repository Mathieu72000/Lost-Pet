package com.example.lostpet.binding

import android.net.Uri
import android.widget.ImageView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import coil.load
import com.example.lostpet.itemAdapter.SpinnerAdapter

@BindingAdapter("entriesType")
fun Spinner.setTypeEntries(entries: List<String>?) {
    if (entries != null) {
        val arrayAdapter =
            SpinnerAdapter(context, android.R.layout.simple_spinner_item, entries)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter = arrayAdapter
    }
}

@BindingAdapter("imageUri")
fun setImageUri(imageView: ImageView, uri: Uri?) {
    imageView.load(uri)
}

