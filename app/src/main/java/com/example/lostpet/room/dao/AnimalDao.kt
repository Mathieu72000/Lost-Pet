package com.example.lostpet.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.lostpet.room.model.Animal
import com.example.lostpet.room.model.AnimalCrossRef

@Dao
interface AnimalDao {

    @Insert
    suspend fun insertAnimal(animal: Animal): Long

    @Transaction
    @Query("SELECT * FROM Animal")
    suspend fun getAllAnimals(): List<AnimalCrossRef>

    @Transaction
    @Query("SELECT * FROM Animal WHERE animalId= :animalId")
    suspend fun getAnimalWithId(animalId: Long): AnimalCrossRef
}