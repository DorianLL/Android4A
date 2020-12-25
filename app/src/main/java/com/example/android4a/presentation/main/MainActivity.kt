package com.example.android4a.presentation.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.android4a.R
import com.example.android4a.presentation.logged.LoggedActivity
import com.example.android4a.presentation.register.RegisterActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

/**
 * The tag given to the [startActivityForResult] of [RegisterActivity]
 */
const val REGISTER_ACTIVITY = 1

/**
 * The tag given to the [startActivityForResult] of [LoggedActivity]
 */
const val LOGGED_ACIVITY = 2

/**
 * The tag given to the [startActivityForResult] result of [LoggedActivity]
 */
const val DISCONNECT = 3

class MainActivity : AppCompatActivity() {

    /**
     * [mainViewModel], the main view model of the main activity
     */
    private val mainViewModel: MainViewModel by inject()

    /**
     * Function called while the app is launched.
     * - Init the layout
     * - The inputs listeners ([initInputsListeners])
     * - The listeners of the buttons ([initOnClickListeners])
     * - The observers of the liveData ([initObservers])
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkIfLogged()
        initInputsListeners()
        initOnClickListeners()
        initObservers()
    }

    /**
     * Waiting for Register activity to send a result the the mainAcitivty in order to fill the email's input
     *
     * @param requestCode:[Int] the code of the finished activity
     * @param resultCode:[Int] indicate if the activity has failed or succeed
     * @param data:[Intent?] data intent from the activity that has been finished
     *
     * @return [Void]
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REGISTER_ACTIVITY -> {
                val email = data?.getStringExtra("email")
                email?.let {
                    login_input.text = (SpannableStringBuilder(email))
                    mainViewModel.onChangedEmail(it)
                    showRegisterSuccessDialog()
                }
            }
            LOGGED_ACIVITY -> {
                val action = data?.getIntExtra("action", 0)
                if (action === DISCONNECT) {
                    val sharedPref: SharedPreferences = this.getSharedPreferences(
                        "ANDROID4A",
                        Context.MODE_PRIVATE
                    )
                    with(sharedPref.edit()) {
                        remove("email")
                        apply()
                    }
                }
            }
        }
    }

    /**
     * Show the error login dialog in case of login fail
     *
     * @return [Void]
     */
    private fun showErrorDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.dialog_login_title))
            .setMessage(resources.getString(R.string.dialog_login_message))
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, _ -> dialog.cancel() }
            .show()
    }

    /**
     * Show a success dialog in case of registered user
     *
     * @return [Void]
     */
    private fun showRegisterSuccessDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.success))
            .setMessage(getString(R.string.created_account))
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, _ -> dialog.cancel() }
            .show()
    }

    /**
     * Function called while login is in
     *
     * @return [Void]
     */
    private fun loginSuccess() {
        val sharedPref: SharedPreferences = this.getSharedPreferences(
            "ANDROID4A",
            Context.MODE_PRIVATE
        )
        val loginSuccess: LoginSuccess = mainViewModel.loginLiveData.value as LoginSuccess
        with(sharedPref.edit()) {
            putString("email", loginSuccess.email)
            apply()
        }
        startLoggedActivity()
    }

    /**
     * Check if user is already logged, if so calls [startLoggedActivity]
     *
     * @return [Void]
     */
    private fun checkIfLogged() {
        val sharedPref: SharedPreferences = this.getSharedPreferences(
            "ANDROID4A",
            Context.MODE_PRIVATE
        )
        if (sharedPref.contains("email")) {
            if (sharedPref.getString("email", null)!!.isNotEmpty()) {
                startLoggedActivity()
            }
        }
    }

    /**
     * Function called to start the logged activity
     *
     * @return [Void]
     */
    private fun startLoggedActivity() {
        val intent = Intent(this@MainActivity, LoggedActivity::class.java)
        startActivityForResult(intent, LOGGED_ACIVITY)
    }

    /**
     * Function's callback of the register button that starts a new activity for result with the tag [REGISTER_ACTIVITY]
     *
     * @return [Void]
     */
    private fun registerButton() {
        val intent = Intent(this@MainActivity, RegisterActivity::class.java)
        startActivityForResult(intent, REGISTER_ACTIVITY)
    }

    /**
     * Function that initialize the inputs listeners in order to put the enabled field of [login_button] to true or false
     *
     * @return [Void]
     */
    private fun initInputsListeners() {
        password_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mainViewModel.onChangedPassword(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        login_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mainViewModel.onChangedEmail(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    /**
     * Init the listeners of the buttons
     * - while clicking on the [login_button], calling [mainViewModel.onClickedLogin] with the fields values
     * - while clicking on the [registerButton], calling [registerButton]
     *
     * @return [Void]
     */
    private fun initOnClickListeners() {
        login_button.setOnClickListener {
            progress_circular.visibility = View.VISIBLE
            mainViewModel.onClickedLogin(
                login_input.text.toString().trim(),
                password_input.text.toString().trim()
            )
        }

        register_button.setOnClickListener {
            registerButton()
        }
    }

    /**
     * Initalize the observers
     * - [mainViewModel.loginLiveData] to observe if the login has succeed or not
     * - [mainViewModel.isEnabledButtonLiveData] to change the enabled value of the [login_button]
     * @return [Void]
     */
    private fun initObservers() {
        mainViewModel.loginLiveData.observe(this, Observer {
            progress_circular.visibility = View.INVISIBLE
            when (it) {
                is LoginSuccess -> loginSuccess()
                LoginError -> showErrorDialog()
            }
        })

        mainViewModel.isEnabledButtonLiveData.observe(this, Observer {
            login_button.isEnabled = mainViewModel.isEnabledButtonLiveData.value!!
        })
    }

}
