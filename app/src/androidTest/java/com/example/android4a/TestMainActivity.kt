package com.example.android4a

import android.app.Activity
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.android4a.local.AppDatabase
import com.example.android4a.local.DatabaseDaoDao
import com.example.android4a.local.models.UserLocal
import com.example.android4a.presentation.main.MainActivity
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
@LargeTest
class TestMainActivity {

    private lateinit var user: UserLocal
    private lateinit var emailToBeTyped: String
    private lateinit var passwordToBeTyped: String

    private var dao: DatabaseDaoDao? = null
    private var db: AppDatabase? = null

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        emailToBeTyped = "testing_user@test.test"
        passwordToBeTyped = "test"
        user = UserLocal(emailToBeTyped, passwordToBeTyped)
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db!!.dataBaseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        dao?.delete(user)
        db!!.close()
    }

    private fun getCurrentActivity(): Activity? {
        val activity = arrayOfNulls<Activity>(1)
        onView(isRoot()).check { view, _ ->
            activity[0] = view.context as Activity
        }
        return activity[0]
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
    fun isLoggingButtonDisabledAndThenEnabled() {
        onView(withId(R.id.login_button)).check(matches(not(isEnabled())))

        onView(withId(R.id.login_input))
            .perform(typeText(emailToBeTyped), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.password_input))
            .perform(typeText(passwordToBeTyped), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.login_button)).check(matches((isEnabled())))
    }

}
