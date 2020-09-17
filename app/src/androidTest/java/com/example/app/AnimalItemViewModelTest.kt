package com.example.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lostpet.repository.AnimalRepository
import com.example.lostpet.viewmodel.AnimalItemViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AnimalItemViewModelTest {

    private val repository by lazy { AnimalRepository() }

    @Test
    fun testFoundVisibility() = runBlocking {
        val animal = repository.getAnimalById("AmlA3UkEa01KJ6Iv33FW")
        val animalItemViewModel = AnimalItemViewModel(animal!!)

        Assert.assertEquals(true, animalItemViewModel.animalCrossRef.found)
        Assert.assertNotNull(animalItemViewModel.getVisibility())
        Assert.assertEquals(4, animalItemViewModel.getVisibility())
    }

    @Test
    fun testLostVisibility() = runBlocking {
        val animal = repository.getAnimalById("6bxGLhlo78vC7RtMLNA2")
        val animalItemViewModel = AnimalItemViewModel(animal!!)

        Assert.assertEquals(false, animalItemViewModel.animalCrossRef.found)
        Assert.assertNotNull(animalItemViewModel.getVisibility())
        Assert.assertEquals(0, animalItemViewModel.getVisibility())
    }
}