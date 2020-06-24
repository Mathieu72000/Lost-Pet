package com.example.lostpet.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.lostpet.room.model.Gender

@Dao
interface GenderDao {

    @Transaction
    @Query("SELECT * FROM gender")
    suspend fun getGender(): List<Gender>
}