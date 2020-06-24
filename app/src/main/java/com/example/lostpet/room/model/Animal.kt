package com.example.lostpet.room.model

import androidx.room.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = Gender::class,
        parentColumns = arrayOf("genderId"),
        childColumns = arrayOf("animalGenderId")
    )],
    indices = [Index("animalGenderId")]
)
data class Animal(
    @PrimaryKey(autoGenerate = true) val animalId: Long,
    @ColumnInfo val animalGenderId: Long?,
    @ColumnInfo val animalTitle: String?,
    @ColumnInfo val animalName: String?,
    @ColumnInfo val species: String?,
    @ColumnInfo val breed: String?,
    @ColumnInfo val identificationNumber: String?,
    @ColumnInfo val color: String?,
    @ColumnInfo val description: String?,
    @ColumnInfo val foundDate: String?,
    @ColumnInfo val location: String?,
    @ColumnInfo val city: String?,
    @ColumnInfo val postalCode: String?,
    @ColumnInfo val country: String?,
    @ColumnInfo val state: String?,
    @ColumnInfo val latitude: Double?,
    @ColumnInfo val longitude: Double?
)