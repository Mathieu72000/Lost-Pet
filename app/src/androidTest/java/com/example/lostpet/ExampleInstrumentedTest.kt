package com.example.lostpet

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lostpet.model.Animal
import com.example.lostpet.repository.AnimalRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private var repository = AnimalRepository()

    @Test
    fun testAnimal() = runBlocking {

        val animal1 = Animal(
            "",
            "Male",
            "Found little cat in a bush",
            "Helyos",
            "Cat",
            "Unknown",
            "None",
            "Black",
            "I found this black little cat in a bush close to my house. He's not afraid of humans. I keep him in my house.",
            "23/08/2020",
            "Le Mans, 72000, France",
            "Le Mans",
            "72000",
            "France",
            "Pays de la Loire",
            48.0152,
            0.1689,
            true,
            ArrayList(),
            "0723587891",
            "jean@gmail.com",
            "FF154F212QFHJGJK"
        )

        repository.addAnimal(animal1)

        val getTestAnimal = flow {
            repository.getAnimal(true).collect {
                emit(it)
            }
        }

        val animalFromCloud1 = getTestAnimal.first { it?.get(0)?.description == "I found this black little cat in a bush close to my house. He's not afraid of humans. I keep him in my house."}

        assertEquals(1, animalFromCloud1?.size)
    }
}
