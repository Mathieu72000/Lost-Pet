package com.example.lostpet.viewmodel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.lostpet.itemAdapter.AnimalItem
import com.example.lostpet.repository.AnimalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

abstract class HomeViewModel : ViewModel() {

    private val repository = AnimalRepository()

    @ExperimentalCoroutinesApi
    private val animalList = liveData(Dispatchers.IO) {
        repository.getAnimal(isFound()).collect {
            emit(it)
        }
    }

    @ExperimentalCoroutinesApi
    val itemList = Transformations.map(animalList) { animal ->
        animal?.map {
            AnimalItem(AnimalItemViewModel(it))
        }
    }

    abstract fun isFound(): Boolean
}