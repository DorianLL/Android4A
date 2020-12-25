package com.example.android4a

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.android4a.presentation.register.RegisterActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class TestRegisterActivity {

    private lateinit var emailToBeTyped: String
    private lateinit var passwordToBeTyped: String

    @get:Rule
    var activityRule: ActivityScenarioRule<RegisterActivity> =
        ActivityScenarioRule(RegisterActivity::class.java)

    @Before
    fun init() {
        emailToBeTyped = "testing_user@test.test"
        passwordToBeTyped = "test"
    }

    @Test
    fun checkIfInputsWorks() {
        onView(withId(R.id.login_input))
            .perform(typeText(emailToBeTyped), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.password_input))
            .perform(typeText(passwordToBeTyped), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.login_input))
            .check(matches(withText(emailToBeTyped)))
        onView(withId(R.id.password_input))
            .check(matches(withText(passwordToBeTyped)))
    }

    @Test
    fun isRegisterButtonDisabledAndThenEnabled() {
        onView(withId(R.id.register_button)).check(matches(not(isEnabled())))

        onView(withId(R.id.login_input))
            .perform(typeText(emailToBeTyped), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.password_input))
            .perform(typeText(passwordToBeTyped), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.register_button)).check(matches((isEnabled())))
    }

}
