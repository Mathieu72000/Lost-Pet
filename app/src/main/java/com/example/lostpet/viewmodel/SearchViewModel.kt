package com.example.lostpet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lostpet.model.Animal
import com.example.lostpet.repository.AnimalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel : ViewModel() {

    private val repository = AnimalRepository()

    fun search() {
        viewModelScope.launch(Dispatchers.IO) {
            val searchAnimal = repository.searchAnimal(
                searchSpecies.value,
                searchBreed.value,
                searchName.value,
                searchPostalCode.value,
                searchColor.value,
                searchIdentificationNumber.value
            )
            Timber.d(searchAnimal.toString())
            animalList.postValue(searchAnimal ?: listOf())
        }
    }

    val searchSpecies = MutableLiveData<String>()
    val searchBreed = MutableLiveData<String>()
    val searchName = MutableLiveData<String>()
    val searchPostalCode = MutableLiveData<String>()
    val searchColor = MutableLiveData<String>()
    val searchIdentificationNumber = MutableLiveData<String>()

    val animalList = MutableLiveData<List<Animal>>()
}