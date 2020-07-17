package com.example.lostpet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lostpet.model.Animal
import com.example.lostpet.repository.AnimalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FormDescriptionViewModel(application: Application) : AndroidViewModel(application) {

    val repository = AnimalRepository()

    val getAnimal = MutableLiveData<Animal?>().apply {
        postValue(null)
    }

    fun getData(animalId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getAnimal.postValue(repository.getAnimalById(animalId))
        }
    }

    val animalId = MutableLiveData<String>()
}