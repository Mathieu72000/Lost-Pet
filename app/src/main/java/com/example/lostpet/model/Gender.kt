package com.example.lostpet.model

import com.google.firebase.firestore.DocumentId

data class Gender(
    @DocumentId val genderId: String,
    val gender: String?
) {
    constructor() : this("", "")
}