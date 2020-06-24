package com.example.lostpet.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lostpet.room.dao.AnimalDao
import com.example.lostpet.room.dao.GenderDao
import com.example.lostpet.room.dao.PicturesDao
import com.example.lostpet.room.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

@Database(
    entities = [Animal::class, Gender::class, Pictures::class],
    version = 1,
    exportSchema = false
)

abstract class LostPetDatabase : RoomDatabase(), CoroutineScope {

    abstract fun animalDao(): AnimalDao
    abstract fun genderDao(): GenderDao
    abstract fun pictureDao(): PicturesDao

    companion object {
        var INSTANCE: LostPetDatabase? = null

        fun getInstance(context: Context): LostPetDatabase? {
            if (INSTANCE == null) {
                synchronized(LostPetDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        LostPetDatabase::class.java,
                        "lostPetDatabase"
                    ).addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            db.execSQL("INSERT into Gender(gender) VALUES ('Male'), ('Female')")
                        }
                    }).build()
                }
            }
            return INSTANCE
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    // ------------------------------------------------------

    suspend fun addAnimal(animal: Animal): Long {
        return this.animalDao().insertAnimal(animal)
    }

    suspend fun getAllAnimals(): List<AnimalCrossRef> =
        this.animalDao().getAllAnimals()

    suspend fun getAnimalWithId(animalId: Long): AnimalCrossRef? =
        this.animalDao().getAnimalWithId(animalId)

    // ---------------------------------

    suspend fun getGender(): List<Gender> {
        return this.genderDao().getGender()
    }

    // --------------------------------

    suspend fun insertPictures(pictures: List<Pictures>){
        this.pictureDao().insertPictures(pictures)
    }

}