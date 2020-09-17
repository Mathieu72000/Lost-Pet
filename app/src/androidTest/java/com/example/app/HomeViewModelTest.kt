package com.example.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lostpet.getOrAwaitValue
import com.example.lostpet.viewmodel.HomeFoundViewModel
import com.example.lostpet.viewmodel.HomeLostViewModel
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    private val homeFoundViewModel by lazy { HomeFoundViewModel() }
    private val homeLostViewModel by lazy { HomeLostViewModel() }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testFoundLiveData() {
        assertEquals(1, homeFoundViewModel.itemList.getOrAwaitValue()?.size)
        assertEquals("Female", homeFoundViewModel.animalList.value?.get(0)?.animalGender)
        assertEquals("Altea", homeFoundViewModel.animalList.value?.get(0)?.animalName)
        assertEquals("Lost cat test", homeFoundViewModel.animalList.value?.get(0)?.animalTitle)
        assertEquals("European", homeFoundViewModel.animalList.value?.get(0)?.breed)
        assertEquals("Le Mans", homeFoundViewModel.animalList.value?.get(0)?.city)
        assertEquals("Black", homeFoundViewModel.animalList.value?.get(0)?.color)
        assertEquals("France", homeFoundViewModel.animalList.value?.get(0)?.country)
        assertEquals(
            "Fake description for test",
            homeFoundViewModel.animalList.value?.get(0)?.description
        )
        assertEquals(true, homeFoundViewModel.animalList.value?.get(0)?.found)
        assertEquals("10.09.2020", homeFoundViewModel.animalList.value?.get(0)?.foundDate)
        assertEquals("Test", homeFoundViewModel.animalList.value?.get(0)?.identificationNumber)
        assertEquals(48.015195666, homeFoundViewModel.animalList.value?.get(0)?.latitude)
        assertEquals(0.168879304649, homeFoundViewModel.animalList.value?.get(0)?.longitude)
        assertEquals(
            "16 Boulevard Charles Nicolle, 72000 Le Mans, France",
            homeFoundViewModel.animalList.value?.get(0)?.location
        )
        assertEquals("72000", homeFoundViewModel.animalList.value?.get(0)?.postalCode)
        assertEquals("Cat", homeFoundViewModel.animalList.value?.get(0)?.species)
        assertEquals("Pays de la Loire", homeFoundViewModel.animalList.value?.get(0)?.state)
        assertEquals(
            "sander95560@gmail.com",
            homeFoundViewModel.animalList.value?.get(0)?.userEmail
        )
        assertEquals(
            "3f4lHm0BC6Qifxyi8ZxbnR43tS92",
            homeFoundViewModel.animalList.value?.get(0)?.userId
        )
        assertEquals("0782947810", homeFoundViewModel.animalList.value?.get(0)?.userPhone)

    }

    @Test
    fun testLostLiveData() {
        assertEquals(1, homeLostViewModel.itemList.getOrAwaitValue()?.size)
        assertEquals("Unknown", homeLostViewModel.animalList.value?.get(0)?.animalGender)
        assertEquals("None", homeLostViewModel.animalList.value?.get(0)?.animalName)
        assertEquals("Test lost dog", homeLostViewModel.animalList.value?.get(0)?.animalTitle)
        assertEquals("Malinois", homeLostViewModel.animalList.value?.get(0)?.breed)
        assertEquals("Le Mans", homeLostViewModel.animalList.value?.get(0)?.city)
        assertEquals("Brown", homeLostViewModel.animalList.value?.get(0)?.color)
        assertEquals("France", homeLostViewModel.animalList.value?.get(0)?.country)
        assertEquals(
            "Lost dog test description",
            homeLostViewModel.animalList.value?.get(0)?.description
        )
        assertEquals(false, homeLostViewModel.animalList.value?.get(0)?.found)
        assertEquals("23/12/2019", homeLostViewModel.animalList.value?.get(0)?.foundDate)
        assertEquals("Test", homeLostViewModel.animalList.value?.get(0)?.identificationNumber)
        assertEquals(48.015195666, homeLostViewModel.animalList.value?.get(0)?.latitude)
        assertEquals(0.168879304649, homeLostViewModel.animalList.value?.get(0)?.longitude)
        assertEquals(
            "16 Boulevard Charles Nicolle, 72000 Le Mans, France",
            homeLostViewModel.animalList.value?.get(0)?.location
        )
        assertEquals("72000", homeLostViewModel.animalList.value?.get(0)?.postalCode)
        assertEquals("Dog", homeLostViewModel.animalList.value?.get(0)?.species)
        assertEquals("Pays de la Loire", homeLostViewModel.animalList.value?.get(0)?.state)
        assertEquals(
            "sander95560@gmail.com",
            homeLostViewModel.animalList.value?.get(0)?.userEmail
        )
        assertEquals(
            "3f4lHm0BC6Qifxyi8ZxbnR43tS92",
            homeLostViewModel.animalList.value?.get(0)?.userId
        )
        assertEquals("0698751258", homeLostViewModel.animalList.value?.get(0)?.userPhone)
    }
}