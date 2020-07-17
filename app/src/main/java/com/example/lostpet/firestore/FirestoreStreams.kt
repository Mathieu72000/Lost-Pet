package com.example.lostpet.firestore

import android.net.Uri
import com.example.lostpet.Constants
import com.example.lostpet.model.Animal
import com.example.lostpet.model.Gender
import com.example.lostpet.model.Pictures
import com.example.lostpet.utils.await
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class FirestoreStreams {

    private val firestoreDatabase = FirebaseFirestore.getInstance()
    private val firestoreStorage = FirebaseStorage.getInstance()


    // ADD

    suspend fun addAnimal(animal: Animal): String? {
        val document = firestoreDatabase.collection(Constants.ANIMAL_COLLECTION).add(animal).await()
        return document?.id
    }

    suspend fun addPictureToStorage(pictures: File): Uri? {
        val reference = firestoreStorage.reference.child("pictures/${pictures.name}")
            .putFile(Uri.fromFile(pictures)).await()
        return reference?.storage?.downloadUrl?.await()
    }

    fun addPictureToCloud(pictureList: Pictures) {
        firestoreDatabase.collection(Constants.PICTURE_COLLECTION).add(pictureList)
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

    suspend fun getPicturesWithId(animalId: String): List<Pictures>? {
        val pictureList: List<Pictures>?
        val collection = firestoreDatabase.collection(Constants.PICTURE_COLLECTION).whereEqualTo("animalPictureId", animalId).get().await()
        pictureList = collection?.toObjects(Pictures::class.java)
        return pictureList
    }

    suspend fun getAnimalWithId(animalId: String): Animal? {
        var animal: Animal? = null
        firestoreDatabase.collection(Constants.ANIMAL_COLLECTION).document(animalId).get().await()
            ?.let {
                animal = it.toObject(Animal::class.java)
            }
        return animal
    }


    suspend fun getGender(): List<Gender> {
        var genderList: List<Gender> = listOf()
        firestoreDatabase.collection(Constants.GENDER_COLLECTION).get()
            .await()?.let {
                genderList = it.toObjects(Gender::class.java)
            }
        return genderList
    }
}