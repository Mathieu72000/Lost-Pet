package com.example.lostpet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lostpet.model.Animal
import com.example.lostpet.model.Gender
import com.example.lostpet.repository.AnimalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FormLostViewModel(application: Application): AndroidViewModel(application) {

    val repository = AnimalRepository()

    fun saveForm(){
        val animalLost = Animal(
            "",
            formLostGenderId.value,
            formLostTitle.value,
            formLostAnimalName.value,
            formLostSpecies.value,
            formLostBreed.value,
            formLostIdentificationNumber.value,
            formLostColor.value,
            formLostDescription.value,
            formLostDate.value,
            formLostLocation.value,
            null,
            null,
            null,
            null,
            latitude,
            longitude,
            false
        )
//        repository.addAnimal(animalLost)
    }

    val genderList = MutableLiveData<List<Gender>>().apply {
        postValue(null)
    }

    fun getLoadData() {
        viewModelScope.launch(Dispatchers.IO) {
//            genderList.postValue(getDatabaseInstance?.getGender())
        }
    }

    val formLostTitle = MutableLiveData<String>()
    val formLostSpecies = MutableLiveData<String>()
    val formLostBreed = MutableLiveData<String>()
    val formLostAnimalName = MutableLiveData<String>()
    val formLostColor = MutableLiveData<String>()
    val formLostDate = MutableLiveData<String>()
    val formLostIdentificationNumber = MutableLiveData<String>()
    val formLostDescription = MutableLiveData<String>()
    val formLostGenderId = MutableLiveData<Long>()
    val formLostLocation = MutableLiveData<String>()
    var latitude: Double? = null
    var longitude: Double? = null
}