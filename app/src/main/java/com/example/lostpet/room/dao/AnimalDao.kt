package com.example.lostpet.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.lostpet.room.model.Animal

@Dao
interface AnimalDao {

    @Insert
    suspend fun insertAnimal(animal: Animal): Long
}