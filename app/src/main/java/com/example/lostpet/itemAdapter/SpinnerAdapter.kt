package com.example.lostpet.itemAdapter

import android.content.Context
import android.widget.ArrayAdapter

open class SpinnerAdapter<T>(context: Context, layoutId: Int, val item: List<T>): ArrayAdapter<T>(context, layoutId, item)