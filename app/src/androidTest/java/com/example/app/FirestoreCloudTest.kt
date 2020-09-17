package com.example.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lostpet.model.Animal
import com.example.lostpet.repository.AnimalRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirestoreCloudTest {

    private val repository by lazy { AnimalRepository() }

    @Test
    fun testAnimal() = runBlocking {
        val animal = Animal(
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
        val id = repository.addAnimal(animal)

        assertNotNull(id)

        val getAnimal = repository.getAnimalById(id!!)
        assertEquals("Male", getAnimal?.animalGender)
        assertEquals("Black", getAnimal?.color)
        assertEquals("Found little cat in a bush", getAnimal?.animalTitle)
        assertEquals("Helyos", getAnimal?.animalName)
        assertEquals("Unknown", getAnimal?.breed)
        assertEquals("None", getAnimal?.identificationNumber)
        assertEquals(
            "I found this black little cat in a bush close to my house. He's not afraid of humans. I keep him in my house.",
            getAnimal?.description
        )
        assertEquals("Le Mans", getAnimal?.city)
        assertEquals("72000", getAnimal?.postalCode)
        assertEquals("France", getAnimal?.country)
        assertEquals("Pays de la Loire", getAnimal?.state)
        assertEquals("23/08/2020", getAnimal?.foundDate)
        assertEquals(48.0152, getAnimal?.latitude)
        assertEquals(0.1689, getAnimal?.longitude)
        assertEquals(true, getAnimal?.found)
        assertEquals("0723587891", getAnimal?.userPhone)
        assertEquals("jean@gmail.com", getAnimal?.userEmail)
        assertEquals("FF154F212QFHJGJK", getAnimal?.userId)

        repository.updateAnimal(
            id,
            "Found a dog",
            "Dog",
            "Malinois",
            "Unknown",
            "White",
            "C12G58SD",
            "0623587488",
            "I found a dog running in the street",
            "Female",
            "09/08/2020"
        )

        val getUpdatedAnimal = repository.getAnimalById(id)

        assertEquals("Female", getUpdatedAnimal?.animalGender)
        assertEquals("White", getUpdatedAnimal?.color)
        assertEquals("Found a dog", getUpdatedAnimal?.animalTitle)
        assertEquals("Unknown", getUpdatedAnimal?.animalName)
        assertEquals("Malinois", getUpdatedAnimal?.breed)
        assertEquals("C12G58SD", getUpdatedAnimal?.identificationNumber)
        assertEquals(
            "I found a dog running in the street",
            getUpdatedAnimal?.description
        )
        assertEquals("Le Mans", getUpdatedAnimal?.city)
        assertEquals("72000", getUpdatedAnimal?.postalCode)
        assertEquals("France", getUpdatedAnimal?.country)
        assertEquals("Pays de la Loire", getUpdatedAnimal?.state)
        assertEquals("09/08/2020", getUpdatedAnimal?.foundDate)
        assertEquals(48.0152, getUpdatedAnimal?.latitude)
        assertEquals(0.1689, getUpdatedAnimal?.longitude)
        assertEquals(true, getUpdatedAnimal?.found)
        assertEquals("0623587488", getUpdatedAnimal?.userPhone)
        assertEquals("jean@gmail.com", getUpdatedAnimal?.userEmail)
        assertEquals("FF154F212QFHJGJK", getUpdatedAnimal?.userId)

        repository.deleteItem(id)
    }
}