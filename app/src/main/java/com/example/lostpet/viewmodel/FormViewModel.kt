package com.example.lostpet.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.lostpet.itemAdapter.PictureItem
import com.example.lostpet.model.Animal
import com.example.lostpet.model.Gender
import com.example.lostpet.model.Pictures
import com.example.lostpet.repository.AnimalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.aprilapps.easyphotopicker.MediaFile

class FormViewModel(application: Application) : AndroidViewModel(application) {

    val context = application.applicationContext

    private val repository = AnimalRepository()

    val genderList = MutableLiveData<List<Gender>>().apply {
        postValue(null)
    }

    val pictureList = MutableLiveData<MutableList<PictureViewModel>>().apply {
        postValue(null)
    }

    // -------------------------------------------------------------------

    fun getGender() {
        viewModelScope.launch(Dispatchers.IO) {
            genderList.postValue(repository.getGender())
        }
    }

    val formTitle = MutableLiveData<String>()
    val formSpecies = MutableLiveData<String>()
    val formBreed = MutableLiveData<String>()
    val formAnimalName = MutableLiveData<String>()
    val formColor = MutableLiveData<String>()
    val formDate = MutableLiveData<String>()
    val formIdentificationNumber = MutableLiveData<String>()
    val formDescription = MutableLiveData<String>()
    val formGenderId = MutableLiveData<Long>()
    var latitude: Double? = null
    var longitude: Double? = null
    var location: String? = null
    var postalCode: String? = null
    var state: String? = null
    var country: String? = null
    var city: String? = null

    val mediatorLiveData = MediatorLiveData<Boolean>().apply {
        addSource(pictureList) {
            postValue(isFormValid())
        }
    }

    private fun isFormValid(): Boolean? {
        return pictureList.value?.isNotEmpty() ?: false
    }

    suspend fun saveForm() {
        val animal = Animal(
            "",
            formGenderId.value,
            formTitle.value,
            formAnimalName.value,
            formSpecies.value,
            formBreed.value,
            formIdentificationNumber.value,
            formColor.value,
            formDescription.value,
            formDate.value,
            location,
            city,
            postalCode,
            country,
            state,
            latitude,
            longitude,
            true
        )
        val animalId = repository.addAnimal(animal)
        if (animalId != null) {
            pictureList.value?.map {
                repository.addPicturesToStorage(it.picture)?.let {
                    Pictures("", it.toString(), animalId)
                }
            }?.map {
                it?.let { picture -> repository.addPictureToCloud(picture) }
            }
        }
    }

    val itemList = Transformations.map(pictureList) { pictures ->
        pictures.map {
            PictureItem(PictureViewModel(it.picture))
        }
    }

    fun addPhoto(photo: List<MediaFile>) {
        pictureList.postValue(photo.map {
            PictureViewModel(it.file)
        }.toMutableList())
    }
}
