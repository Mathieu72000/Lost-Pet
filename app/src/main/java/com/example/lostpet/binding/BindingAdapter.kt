package com.example.lostpet.binding

import android.R
import android.net.Uri
import android.widget.ImageView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.lostpet.itemAdapter.SpinnerAdapter

@BindingAdapter("entriesType")
fun Spinner.setTypeEntries(entries: List<String>?) {
    if (entries != null) {
        val arrayAdapter =
            SpinnerAdapter(context, R.layout.simple_spinner_item, entries)
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        adapter = arrayAdapter
    }
}

@BindingAdapter("imageUri")
fun setImageUri(imageView: ImageView, uri: Uri?) {
    Glide.with(imageView)
        .load(uri)
        .into(imageView)
}

