package com.example.lostpet.repository

import android.net.Uri
import com.example.lostpet.firestore.FirestoreStreams
import com.example.lostpet.model.Animal
import com.example.lostpet.model.Gender
import com.example.lostpet.model.Pictures
import java.io.File

class AnimalRepository {

    private val fireStoreStreams: FirestoreStreams = FirestoreStreams()

    // ADD

    suspend fun addAnimal(animal: Animal): String? {
        return fireStoreStreams.addAnimal(animal)
    }

    suspend fun addPicturesToStorage(pictures: File): Uri? {
        return fireStoreStreams.addPictureToStorage(pictures)
    }

    fun addPictureToCloud(pictureList: Pictures) {
        fireStoreStreams.addPictureToCloud(pictureList)
    }

    // GET

    suspend fun getFoundAnimal(lostOrFound: Boolean): List<Animal> {
        return fireStoreStreams.getFoundAnimal(lostOrFound)
    }

    suspend fun getAnimalById(animalId: String): Animal? {
        return fireStoreStreams.getAnimalWithId(animalId)
    }

    suspend fun getPictureWithId(animalId: String): List<Pictures>? {
        return fireStoreStreams.getPicturesWithId(animalId)
    }

    suspend fun getGender(): List<Gender> {
        return fireStoreStreams.getGender()
    }
}