package com.example.lostpet.model

import com.google.firebase.firestore.DocumentId
import java.io.Serializable

data class Animal(
    @DocumentId val animalId: String,
    val animalGenderId: Long?,
    val animalTitle: String?,
    val animalName: String?,
    val species: String?,
    val breed: String?,
    val identificationNumber: String?,
    val color: String?,
    val description: String?,
    val foundDate: String?,
    val location: String?,
    val city: String?,
    val postalCode: String?,
    val country: String?,
    val state: String?,
    val latitude: Double?,
    val longitude: Double?,
    val isFound: Boolean?
): Serializable {

    constructor():this("",0L, "", "", "", "", "", "", "", "", "", "", "", "",
    "", 0.0, 0.0, true)
}