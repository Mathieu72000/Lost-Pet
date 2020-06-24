package com.example.lostpet.room.model

import androidx.room.*


@Entity(
    foreignKeys = [ForeignKey(
        entity = Animal::class,
        parentColumns = arrayOf("animalId"),
        childColumns = arrayOf("animalPictureId")
    )],
    indices = [Index("animalPictureId")]
)
data class Pictures(
    @PrimaryKey(autoGenerate = true) val pictureId: Long = 0,
    @ColumnInfo val picture: String?,
    @ColumnInfo val animalPictureId: Long?
)