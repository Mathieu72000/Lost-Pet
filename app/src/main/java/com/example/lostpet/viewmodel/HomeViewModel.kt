package com.example.lostpet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.lostpet.itemAdapter.AnimalItem
import com.example.lostpet.room.database.LostPetDatabase
import com.example.lostpet.room.model.AnimalCrossRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val getDatabaseInstance = LostPetDatabase.getInstance(application)

    var animalList = MutableLiveData<List<AnimalCrossRef>>()

    val itemList = Transformations.map(animalList) { animal ->
        animal.map {
            AnimalItem(AnimalItemViewModel(it))
        }
    }

    fun getAnimal(){
        viewModelScope.launch(Dispatchers.IO) {
            animalList.postValue(getDatabaseInstance?.getAllAnimals())
        }
    }
}