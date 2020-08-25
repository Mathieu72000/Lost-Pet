package com.example.lostpet.repository

import com.example.lostpet.firestore.FirestoreStreams
import com.example.lostpet.model.Animal
import com.example.lostpet.model.User
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestoreSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class AnimalRepository {

    private val fireStoreStreams: FirestoreStreams = FirestoreStreams()

    // ADD

    suspend fun addAnimal(animal: Animal): String? {
        return fireStoreStreams.addAnimal(animal)
    }

    suspend fun addPicturesToStorage(pictures: String): ArrayList<String>? {
        return fireStoreStreams.addPictureToStorage(pictures)
    }

    // GET

    @ExperimentalCoroutinesApi
    suspend fun getAnimal(isFound: Boolean): Flow<List<Animal>?> {
        return fireStoreStreams.getAnimal(isFound)
    }

    suspend fun getAnimalById(animalId: String): Animal? {
        return fireStoreStreams.getAnimalWithId(animalId)
    }

    @ExperimentalCoroutinesApi
    suspend fun getUserCollection(userId: String): Flow<List<Animal>> {
        return fireStoreStreams.getUserCollection(userId)
    }

    suspend fun getGender(): List<String>? {
        return fireStoreStreams.getGender()
    }

    suspend fun searchAnimal(species: String?, breed: String?, name: String?, postalCode: String?, color: String?, identificationNumber: String?): List<Animal>? {
        return fireStoreStreams.searchAnimal(species, breed, name, postalCode, color, identificationNumber)
    }
//
//    suspend fun updateItem(documentId: String, animal: Animal){
//        fireStoreStreams.updateItem(documentId, animal)
//    }

    suspend fun testUpdate(documentId: String, title: String?, species: String?, breed: String?, animalName: String?, color: String?, identificationNumber: String?, phoneNumber: String?, description: String?, gender: String?, date: String){
        fireStoreStreams.updateItem(documentId, title, species, breed, animalName, color, identificationNumber, phoneNumber, description, gender, date)
    }

    suspend fun deleteItem(documentId: String){
        fireStoreStreams.deleteItem(documentId)
    }
}