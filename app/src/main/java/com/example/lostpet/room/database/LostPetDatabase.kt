package com.example.lostpet.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lostpet.room.dao.AnimalDao
import com.example.lostpet.room.dao.UserDao
import com.example.lostpet.room.model.Animal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

@Database(entities = [Animal::class], version = 1, exportSchema = false)

abstract class LostPetDatabase : RoomDatabase(), CoroutineScope {

    abstract fun animalDao() : AnimalDao
    abstract fun userDao() : UserDao

    companion object {
        var INSTANCE: LostPetDatabase? = null

        fun getInstance(context: Context): LostPetDatabase? {
            if (INSTANCE == null) {
                synchronized(LostPetDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        LostPetDatabase::class.java,
                        "lostPetDatabase"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    // ------------------------------------------------------

//    suspend fun insertUser(user: User): Long = userDao().insertUser(user)
}