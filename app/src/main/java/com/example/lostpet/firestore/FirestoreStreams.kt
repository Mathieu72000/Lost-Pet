package com.example.lostpet.firestore

import android.net.Uri
import com.example.lostpet.Constants
import com.example.lostpet.model.Animal
import com.example.lostpet.model.User
import com.example.lostpet.utils.await
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.util.*

class FirestoreStreams {

    private val firestoreDatabase = FirebaseFirestore.getInstance()
    private val firestoreStorage = FirebaseStorage.getInstance()

    // ADD

    suspend fun addAnimal(animal: Animal): String? {
        val document = firestoreDatabase.collection(Constants.ANIMAL_COLLECTION).add(animal).await()
        return document?.id
    }

    suspend fun addUser(user: User, animalId: String) {
        firestoreDatabase.collection(Constants.USER_COLLECTION).document(animalId).set(user).await()
    }

    suspend fun addPictureToStorage(pictures: String): ArrayList<String>? {
        val urlArray: ArrayList<String>? = arrayListOf()
        val reference = firestoreStorage.reference.child("pictures/${UUID.randomUUID()}")
            .putFile(Uri.fromFile(File(pictures))).await()
        urlArray?.add(reference?.storage?.downloadUrl?.await().toString())
        return urlArray
    }

    // GET

    suspend fun getFoundAnimal(lostOrFound: Boolean): List<Animal> {
        var animalList: List<Animal> = listOf()
        firestoreDatabase.collection(Constants.ANIMAL_COLLECTION).whereEqualTo("found", lostOrFound)
            .get().await()?.let {
                animalList = it.toObjects(Animal::class.java)
            }
        return animalList
    }

    suspend fun getAnimalWithId(animalId: String): Animal? {
        var animal: Animal? = null
        firestoreDatabase.collection(Constants.ANIMAL_COLLECTION).document(animalId).get().await()
            ?.let {
                animal = it.toObject(Animal::class.java)
            }
        return animal
    }

    suspend fun getGender(): List<String>? {
        val collection = firestoreDatabase.collection(Constants.GENDER_COLLECTION).get().await()
        val documentId = collection?.documents?.map {
            it.id
        }
        return documentId?.toList()
    }
}