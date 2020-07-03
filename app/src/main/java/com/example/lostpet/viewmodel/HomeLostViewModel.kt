package com.example.lostpet.viewmodel

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.lostpet.itemAdapter.LostAnimalItem
import com.example.lostpet.room.database.LostPetDatabase
import com.example.lostpet.room.model.AnimalCrossRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeLostViewModel(application: Application) : AndroidViewModel(application) {

    private val getDatabaseInstance = LostPetDatabase.getInstance(application)

    @VisibleForTesting
    private val animalList = MutableLiveData<List<AnimalCrossRef>>()

    val itemList = Transformations.map(animalList) { animal ->
        animal.map {
            LostAnimalItem(AnimalItemViewModel(it))
        }
    }

    fun getLostAnimal() {
        viewModelScope.launch(Dispatchers.IO) {
            animalList.postValue(getDatabaseInstance?.getFoundAnimal(false))
        }
    }
}