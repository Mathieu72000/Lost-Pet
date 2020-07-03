package com.example.lostpet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.lostpet.room.database.LostPetDatabase
import com.example.lostpet.room.model.AnimalCrossRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FormDescriptionViewModel(application: Application): AndroidViewModel(application) {

    private val getDatabase = LostPetDatabase.getInstance(application)

    val getAnimal = MutableLiveData<AnimalCrossRef>().apply {
        postValue(null)
    }

    val animalId = MutableLiveData<Long>()

    fun getData(animalId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getAnimal.postValue(getDatabase?.getAnimalWithId(animalId))
        }
    }
}