package com.example.lostpet.binding

import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.example.lostpet.itemAdapter.SpinnerAdapter
import com.example.lostpet.room.model.Gender

@BindingAdapter("entriesType")
fun Spinner.setTypeEntries(entries: List<Gender>?){
    if(entries != null){
        val arrayAdapter = object : SpinnerAdapter<Gender?>(context, android.R.layout.simple_spinner_item, entries){
            override fun getItemId(position: Int): Long {
                return entries[position].genderId
            }
        }
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter = arrayAdapter
    }

    @BindingAdapter("srcBase64")
    fun ImageView.setBase64(entries: String?){
        val decodeBase64 = Base64.decode(entries, Base64.DEFAULT)
        val decodeByteArray = BitmapFactory.decodeByteArray(decodeBase64, 0, decodeBase64.size)
        setImageBitmap(decodeByteArray)
    }
}
