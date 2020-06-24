package com.example.lostpet.room.model

import androidx.room.Embedded
import androidx.room.Relation

data class AnimalCrossRef(
    @Embedded
    val animal: Animal,
    @Relation(
        entity = Gender::class,
        parentColumn = "animalGenderId",
        entityColumn = "genderId"
    )
    val gender: Gender,
    @Relation(
        entity = Pictures::class,
        parentColumn = "animalId",
        entityColumn = "animalPictureId"
    )
    val pictures: List<Pictures>
)