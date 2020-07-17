package com.example.lostpet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lostpet.itemAdapter.AnimalItem
import com.example.lostpet.model.Animal
import com.example.lostpet.repository.AnimalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = AnimalRepository()

    val animalList = MutableLiveData<List<Animal>>()

    val itemList = Transformations.map(animalList) { animal ->
        animal.map {
            AnimalItem(AnimalItemViewModel(it))
        }
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            animalList.postValue(repository.getFoundAnimal(true))
        }
    }
}