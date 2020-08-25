package com.example.lostpet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.lostpet.itemAdapter.AnimalItem
import com.example.lostpet.model.Animal

class SearchResultViewModel : ViewModel() {

    var animal = MutableLiveData<MutableList<Animal>>()

    val itemList = Transformations.map(animal) { animal ->
        animal.map {
            AnimalItem(AnimalItemViewModel(it))
        }
    }
}