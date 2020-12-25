package com.example.android4a.presentation.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.android4a.R
import com.example.android4a.presentation.main.REGISTER_ACTIVITY
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.login_input
import kotlinx.android.synthetic.main.activity_register.password_input
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity() {

    private val registerViewModel: RegisterViewModel by inject()

    /**
     * Function called while the activity register is launched.
     * - Init the layout
     * - The inputs listeners ([initInputsListeners])
     * - The listeners of the buttons ([initOnClickListeners])
     * - The observers of the liveData ([initObservers])
     *
     * @return [Void]
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // to show the back button on the top left corner on the app bar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        initInputsListeners()
        initObservers()
        initOnClickListeners()
    }

    /**
     * Function that initialize the inputs listeners in order to put the enabled field of [register_button] to true or false
     *
     * @return [Void]
     */
    private fun initInputsListeners() {
        password_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                registerViewModel.onChangedPassword(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        login_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                registerViewModel.onChangedEmail(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    /**
     * Initalize the observers
     * - [registerViewModel.registerLiveData] to observe if the register task has succeed or not
     * - [registerViewModel.isEnabledButtonLiveData] to change the enabled value of the [register_button]
     *
     * @return [Void]
     */
    private fun initObservers() {
        registerViewModel.isEnabledButtonLiveData.observe(this, Observer {
            register_button.isEnabled = registerViewModel.isEnabledButtonLiveData.value!!
        })

        registerViewModel.registerLiveData.observe(this, Observer {
            progress_circular.visibility = View.INVISIBLE
            when (it) {
                is RegisterSuccess -> registerSuccess()
                RegisterError -> showErrorDialog()
            }
        })
    }

    /**
     * Init the listeners of the buttons
     * - while clicking on the [registerButton], calling [registerViewModel.onClickedRegister]
     *
     * @return [Void]
     */
    private fun initOnClickListeners() {
        register_button.setOnClickListener {
            progress_circular.visibility = View.VISIBLE
            registerViewModel.onClickedRegister(
                login_input.text.toString().trim(),
                password_input.text.toString().trim()
            )
        }
    }

    /**
     * Show the error register dialog in case of register fail
     *
     * @return [Void]
     */
    private fun showErrorDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.error_register_dialog))
            .setMessage(getString(R.string.taken_email))
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, _ -> dialog.cancel() }
            .show()
    }

    /**
     * In case of registering success, finish activity for result
     *
     * @return [Void]
     */
    private fun registerSuccess() {
        val email: String = login_input.text.toString()
        val intent = Intent()
        intent.putExtra("email", email)
        setResult(REGISTER_ACTIVITY, intent)
        finish()
    }
}
