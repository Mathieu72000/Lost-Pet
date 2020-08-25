package com.example.lostpet.firestore

import android.net.Uri
import com.example.lostpet.Constants
import com.example.lostpet.model.Animal
import com.example.lostpet.model.User
import com.example.lostpet.utils.await
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    suspend fun addPictureToStorage(pictures: String): ArrayList<String>? {
        val urlArray: ArrayList<String>? = arrayListOf()
        val reference = firestoreStorage.reference.child("pictures/${UUID.randomUUID()}")
            .putFile(Uri.fromFile(File(pictures))).await()
        urlArray?.add(reference?.storage?.downloadUrl?.await().toString())
        return urlArray
    }

    // GET

    @ExperimentalCoroutinesApi
    suspend fun getAnimal(isFound: Boolean): Flow<List<Animal>> = callbackFlow {
        val collection =
            firestoreDatabase.collection(Constants.ANIMAL_COLLECTION).whereEqualTo("found", isFound)
        val subscription = collection.addSnapshotListener { snapshot, _ ->
            val animal = snapshot?.toObjects(Animal::class.java) ?: listOf()
            offer(animal)
        }
        awaitClose { subscription.remove() }
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

    @ExperimentalCoroutinesApi
    suspend fun getUserCollection(userId: String): Flow<List<Animal>> = callbackFlow {
        val collection =
            firestoreDatabase.collection(Constants.ANIMAL_COLLECTION).whereEqualTo("userId", userId)
        val subscription = collection.addSnapshotListener { value, _ ->
            val userCollection = value?.toObjects(Animal::class.java) ?: listOf()
            offer(userCollection)
        }
        awaitClose { subscription.remove() }
    }

    suspend fun searchAnimal(
        species: String?,
        breed: String?,
        name: String?,
        postalCode: String?,
        color: String?,
        identificationNumber: String?
    ): List<Animal>? {
        val collection = firestoreDatabase.collection(Constants.ANIMAL_COLLECTION).apply {
            if (!species.isNullOrEmpty()) {
                whereEqualTo("species", species)
            }
            if (!breed.isNullOrEmpty()) {
                whereEqualTo("breed", breed)
            }
            if (!name.isNullOrEmpty()) {
                whereEqualTo("animalName", name)
            }
            if (!postalCode.isNullOrEmpty()) {
                whereEqualTo("postalCode", postalCode)
            }
            if (!color.isNullOrEmpty()) {
                whereEqualTo("color", color)
            }
            if (!identificationNumber.isNullOrEmpty()) {
                whereEqualTo("identificationNumber", identificationNumber)
            }
        }
        return collection.get().await()?.toObjects(Animal::class.java)
    }

//    suspend fun searchAnimal(
//        species: String?,
//        breed: String?,
//        name: String?,
//        postalCode: String?,
//        color: String?,
//        identificationNumber: String?
//    ): List<Animal>? {
//        val collection = firestoreDatabase.collection(Constants.ANIMAL_COLLECTION)
//            .whereEqualTo("species", species)
//            .whereEqualTo("breed", breed)
//            .whereEqualTo("animalName", name)
//            .whereEqualTo("postalCode", postalCode)
//            .whereEqualTo("color", color)
//            .whereEqualTo("identificationNumber", identificationNumber).get().await()
//        return collection?.toObjects(Animal::class.java)
//    }

    suspend fun updateItem(documentId: String, animal: Animal) {
        firestoreDatabase.collection(Constants.ANIMAL_COLLECTION).document(documentId)
            .set(animal, SetOptions.merge()).await()
    }

    suspend fun updateItem(
        documentId: String,
        title: String?,
        species: String?,
        breed: String?,
        animalName: String?,
        color: String?,
        identificationNumber: String?,
        phoneNumber: String?,
        description: String?,
        gender: String?,
        date: String?
    ) {
        firestoreDatabase.collection(Constants.ANIMAL_COLLECTION).document(documentId).update(
            "animalTitle",
            title,
            "species",
            species,
            "breed",
            breed,
            "animalName",
            animalName,
            "color",
            color,
            "identificationNumber",
            identificationNumber,
            "userPhone",
            phoneNumber,
            "description",
            description,
            "animalGender",
            gender,
            "foundDate",
            date
        ).await()
    }

    suspend fun deleteItem(documentId: String) {
        firestoreDatabase.collection(Constants.ANIMAL_COLLECTION).document(documentId).delete()
            .await()
    }
}