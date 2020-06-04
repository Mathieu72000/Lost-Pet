package com.example.lostpet.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Animal(
    @PrimaryKey(autoGenerate = true) val animalId: Long,
    @ColumnInfo val animal: String?,
    @ColumnInfo val breed: String?,
    @ColumnInfo val identificationNumber: String?,
    @ColumnInfo val gender: String?,
    @ColumnInfo val color: String?,
    @ColumnInfo val description: String?,
    @ColumnInfo val foundDate: String?,
    @ColumnInfo val location: String?,
    @ColumnInfo val latitude: Double?,
    @ColumnInfo val longitude: Double?
)