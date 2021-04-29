package com.example.lostpet

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.xwray.groupie.GroupieViewHolder
import org.junit.Rule
import org.junit.Test

@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun performClickOnRecyclerViewItem() {
        Espresso.onView(withId(R.id.main_fragment_recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<GroupieViewHolder>(
                0,
                click()
            )
        )

        Espresso.onView(withId(R.id.location_tv))
            .check(ViewAssertions.matches(withText("16 Boulevard Charles Nicolle, 72000 Le Mans, France")))
        Espresso.onView(withId(R.id.description_tv))
            .check(ViewAssertions.matches(withText("Fake description for test")))
        Espresso.onView(withId(R.id.color_tv))
            .check(ViewAssertions.matches(withText("Black")))
        Espresso.onView(withId(R.id.species_tv))
            .check(ViewAssertions.matches(withText("Cat")))
        Espresso.onView(withId(R.id.gender_tv))
            .check(ViewAssertions.matches(withText("Female")))
        Espresso.onView(withId(R.id.name_tv))
            .check(ViewAssertions.matches(withText("Altea")))
        Espresso.onView(withId(R.id.identification_number_tv))
            .check(ViewAssertions.matches(withText("Test")))
        Espresso.onView(withId(R.id.date_tv))
            .check(ViewAssertions.matches(withText("10.09.2020")))
        Espresso.onView(withId(R.id.breed_tv))
            .check(ViewAssertions.matches(withText("European")))

    }

    @Test
    fun performClickOnSettings() {
        Espresso.onView(withId(R.id.settings)).perform(click())
        Espresso.onView(withId(R.id.notification_switch)).perform(click())
        Espresso.onView(withId(R.id.notification_switch)).perform(click())
        Espresso.onView(withId(R.id.settings_disconnect_button)).perform(click())
        Espresso.onView(withText("No")).perform(click())
        Espresso.onView(withId(R.id.settings_delete_button)).perform(click())
        Espresso.onView(withText("No")).perform(click())
    }

    @Test
    fun performClickOnSearch() {
        Espresso.onView(withId(R.id.search)).perform(click())
        Espresso.onView(withId(R.id.search_species_editText)).perform(click())
            .perform(replaceText("Male"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(withId(R.id.search_button)).perform(click())
    }
}