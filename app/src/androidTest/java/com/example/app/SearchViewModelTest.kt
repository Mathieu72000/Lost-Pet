package com.example.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lostpet.getOrAwaitValue
import com.example.lostpet.repository.AnimalRepository
import com.example.lostpet.viewmodel.SearchViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchViewModelTest {

    private val searchViewModel by lazy { SearchViewModel() }

    private val repository by lazy { AnimalRepository() }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testValidSearch() = runBlocking {
        searchViewModel.searchBreed.postValue("Malinois")
        searchViewModel.searchColor.postValue("Brown")
        searchViewModel.searchIdentificationNumber.postValue("Test")
        searchViewModel.searchName.postValue("None")
        searchViewModel.searchSpecies.postValue("Dog")
        searchViewModel.searchPostalCode.postValue("72000")

        val searchAnimal = repository.searchAnimal(
            searchViewModel.searchSpecies.value,
            searchViewModel.searchBreed.value,
            searchViewModel.searchName.value,
            searchViewModel.searchPostalCode.value,
            searchViewModel.searchColor.value,
            searchViewModel.searchIdentificationNumber.value
        )

        searchViewModel.animalList.postValue(searchAnimal)

        assertEquals(1, searchViewModel.animalList.getOrAwaitValue()?.size)
    }

    @Test
    fun testNotValidTest() = runBlocking {
        searchViewModel.searchBreed.postValue("Unknown")
        searchViewModel.searchColor.postValue("Brown")
        searchViewModel.searchIdentificationNumber.postValue("Test")
        searchViewModel.searchName.postValue("None")
        searchViewModel.searchSpecies.postValue("Dog")
        searchViewModel.searchPostalCode.postValue("72000")

        val searchAnimal = repository.searchAnimal(
            searchViewModel.searchSpecies.value,
            searchViewModel.searchBreed.value,
            searchViewModel.searchName.value,
            searchViewModel.searchPostalCode.value,
            searchViewModel.searchColor.value,
            searchViewModel.searchIdentificationNumber.value
        )

        searchViewModel.animalList.postValue(searchAnimal)

        assertEquals(0, searchViewModel.animalList.getOrAwaitValue()?.size)
    }

    @Test
    fun testValidOneFieldSearch() = runBlocking {
        searchViewModel.searchPostalCode.postValue("72000")

        val searchAnimal = repository.searchAnimal(
            searchViewModel.searchSpecies.value,
            searchViewModel.searchBreed.value,
            searchViewModel.searchName.value,
            searchViewModel.searchPostalCode.value,
            searchViewModel.searchColor.value,
            searchViewModel.searchIdentificationNumber.value
        )

        searchViewModel.animalList.postValue(searchAnimal)

        assertEquals(2, searchViewModel.animalList.getOrAwaitValue()?.size)
    }

    @Test
    fun testNotValidOneFieldSearch() = runBlocking {
        searchViewModel.searchPostalCode.postValue("93000")

        val searchAnimal = repository.searchAnimal(
            searchViewModel.searchSpecies.value,
            searchViewModel.searchBreed.value,
            searchViewModel.searchName.value,
            searchViewModel.searchPostalCode.value,
            searchViewModel.searchColor.value,
            searchViewModel.searchIdentificationNumber.value
        )

        searchViewModel.animalList.postValue(searchAnimal)

        assertEquals(0, searchViewModel.animalList.getOrAwaitValue()?.size)
    }
}