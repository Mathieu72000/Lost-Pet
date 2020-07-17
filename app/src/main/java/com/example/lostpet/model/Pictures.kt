package com.example.lostpet.model

import com.google.firebase.firestore.DocumentId


data class Pictures(
    @DocumentId val pictureId: String, val url: String?, val animalPictureId: String?
) {
    constructor() : this("", null, "")
}