package com.example.android4a.presentation.main

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android4a.domain.usecase.GetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * View model of the [MainActivity]
 *
 * @param getUserUseCase get a user
 */
class MainViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    /**
     * loginLiveData [MutableLiveData] of [LoginStatus] that contains login status
     */
    internal val loginLiveData: MutableLiveData<LoginStatus> = MutableLiveData()

    /**
     * loginLiveData [MutableLiveData] of [String] that contains password value
     */
    private val passwordUserLiveData: MutableLiveData<String> = MutableLiveData()

    /**
     * loginLiveData [MutableLiveData] of [String] that contains email value
     */
    private val emailUserLiveData: MutableLiveData<String> = MutableLiveData()

    /**
     * loginLiveData [MutableLiveData] of [Boolean] that contains register_button visibility state
     */
    internal val isEnabledButtonLiveData: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * Callback while login button is pressed
     *
     * @param emailUser Email of the user
     * @param passwordUser Password of the user
     *
     * @return [Void]
     */
    fun onClickedLogin(emailUser: String, passwordUser: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = getUserUseCase.invoke(emailUser)
            val loginStatus =
                if (user?.password == passwordUser) LoginSuccess(user.email) else LoginError
            //Asynchronous way to notify data changes to the observer
            loginLiveData.postValue(loginStatus)
        }
    }

    /**
     * Callback while password value has changed.
     * Execute then [isDisabledButton]
     *
     * @param passwordUser Password of the user
     * @return [Void]
     */
    fun onChangedPassword(passwordUser: String) {
        passwordUserLiveData.value = passwordUser
        isDisabledButton()
    }

    /**
     * Callback while password value has changed.
     * Execute then [isDisabledButton]
     *
     * @param emailUser Email of the user
     * @return [Void]
     */
    fun onChangedEmail(emailUser: String) {
        emailUserLiveData.value = emailUser
        isDisabledButton()
    }

    /**
     * Function that check if an email is correct
     *
     * @return [Boolean] if email is correct
     */
    private fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this.trim())
            .matches()
    }

    /**
     * Function to check if [passwordUserLiveData] and [emailUserLiveData] are corrects
     *
     * @return [Void]
     */
    private fun isDisabledButton() {
        if (passwordUserLiveData.value == null || emailUserLiveData.value == null)
            isEnabledButtonLiveData.postValue(false)
        else if (passwordUserLiveData.value?.isNotEmpty()!! && emailUserLiveData.value?.isNotEmpty()!! && emailUserLiveData.value!!.isEmailValid())
            isEnabledButtonLiveData.postValue(true)
        else
            isEnabledButtonLiveData.postValue(false)

    }
}