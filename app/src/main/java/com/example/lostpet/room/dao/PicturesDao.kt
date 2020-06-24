package com.example.lostpet.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.lostpet.room.model.Pictures

@Dao
interface PicturesDao {

    @Insert
    suspend fun insertPictures(pictures: List<Pictures>)
}