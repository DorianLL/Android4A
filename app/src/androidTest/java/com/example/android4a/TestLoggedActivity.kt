package com.example.android4a

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.android4a.presentation.logged.LoggedActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class TestLoggedActivity {

    private lateinit var search: String

    @get:Rule
    var activityRule: ActivityScenarioRule<LoggedActivity> =
        ActivityScenarioRule(LoggedActivity::class.java)

    @Before
    fun init() {
        search = "call of"
    }

    @Test
    fun checkIfInputsWorks() {
        onView(withId(R.id.search_input))
            .perform(ViewActions.typeText(search), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.search_input)).perform(ViewActions.click())

        onView(withId(R.id.search_input))
            .check(ViewAssertions.matches(ViewMatchers.withText(search)))
    }

}
