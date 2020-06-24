package com.example.lostpet.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.util.Base64
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.lostpet.itemAdapter.PictureItem
import com.example.lostpet.room.database.LostPetDatabase
import com.example.lostpet.room.model.Animal
import com.example.lostpet.room.model.Gender
import com.example.lostpet.room.model.Pictures
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.aprilapps.easyphotopicker.MediaFile
import java.io.ByteArrayOutputStream

class FormViewModel(application: Application) : AndroidViewModel(application) {

    private val getDatabaseInstance = LostPetDatabase.getInstance(application)

    private suspend fun insertAnimal(animal: Animal): Long =
        getDatabaseInstance?.addAnimal(animal) ?: -1

    private suspend fun insertPictures(pictures: List<Pictures>) =
        getDatabaseInstance?.insertPictures(pictures)

    // -------------------------------------------------------------------


    fun getLoadData() {
        viewModelScope.launch(Dispatchers.IO) {
            genderList.postValue(getDatabaseInstance?.getGender())
        }
    }

    val genderList = MutableLiveData<List<Gender>>().apply {
        postValue(null)
    }

    val pictureList = MutableLiveData<MutableList<PictureViewModel>>().apply {
        postValue(mutableListOf())
    }

    val itemList = Transformations.map(pictureList) { pictures ->
        pictures.map {
            PictureItem(
                PictureViewModel(it.base64)
            )
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

    suspend fun saveForm() {
        val animal = Animal(
            0,
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
            longitude
        )
//        val animalId = addAnimal(animal)
        val animalId = insertAnimal(animal)
        if (animalId != -1L) {
            pictureList.value?.map {
                Pictures(0, it.base64, animalId)
            }?.let {
                insertPictures(it)
            }
        }
    }

    fun addPictures(photo: List<MediaFile>) {
        pictureList.postValue(pictureList.value?.union(photo.map {
            val bitmap = BitmapFactory.decodeFile(it.file.path)
            val byteArray = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArray)
            val toByteArray = byteArray.toByteArray()
            val base64 = Base64.encodeToString(toByteArray, Base64.DEFAULT)
            PictureViewModel(base64)
        })?.toMutableList())
    }
}