package com.example.lostpet.viewmodel

import android.view.View
import com.example.lostpet.model.Animal

data class AnimalItemViewModel(val animalCrossRef: Animal) {

    fun getVisibility(): Int {
        return if (animalCrossRef.found == false) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }
}

