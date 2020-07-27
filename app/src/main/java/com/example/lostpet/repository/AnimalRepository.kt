package com.example.lostpet.repository

import com.example.lostpet.firestore.FirestoreStreams
import com.example.lostpet.model.Animal
import com.example.lostpet.model.User

class AnimalRepository {

    private val fireStoreStreams: FirestoreStreams = FirestoreStreams()

    // ADD

    suspend fun addAnimal(animal: Animal): String? {
        return fireStoreStreams.addAnimal(animal)
    }

    suspend fun addUser(user: User, animalId: String) {
        return fireStoreStreams.addUser(user, animalId)
    }

    suspend fun addPicturesToStorage(pictures: String): ArrayList<String>? {
        return fireStoreStreams.addPictureToStorage(pictures)
    }

    // GET

    suspend fun getFoundAnimal(lostOrFound: Boolean): List<Animal> {
        return fireStoreStreams.getFoundAnimal(lostOrFound)
    }

    suspend fun getAnimalById(animalId: String): Animal? {
        return fireStoreStreams.getAnimalWithId(animalId)
    }

    suspend fun getGender(): List<String>? {
        return fireStoreStreams.getGender()
    }
}