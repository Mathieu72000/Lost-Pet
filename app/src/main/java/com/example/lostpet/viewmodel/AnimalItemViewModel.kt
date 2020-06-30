package com.example.lostpet.viewmodel

import com.example.lostpet.room.model.AnimalCrossRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

data class AnimalItemViewModel(val animalCrossRef: AnimalCrossRef) {

    fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    val userName = getCurrentUser()?.displayName
    val userPicture = getCurrentUser()?.photoUrl
    val userEmail = getCurrentUser()?.email


}