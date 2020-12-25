package com.example.android4a.presentation.register

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android4a.domain.entity.User
import com.example.android4a.domain.usecase.CreateUserUseCase
import com.example.android4a.domain.usecase.GetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val passwordUserLiveData: MutableLiveData<String> = MutableLiveData()
    private val emailUserLiveData: MutableLiveData<String> = MutableLiveData()
    internal val isEnabledButtonLiveData: MutableLiveData<Boolean> = MutableLiveData()
    internal val registerLiveData: MutableLiveData<RegisterStatus> = MutableLiveData()

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
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
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

    /**
     * CallBack while register button is pressed
     *
     * @param emailUser Email of the user
     * @param passwordUser Password of the user
     * @return [Void]
     */
    fun onClickedRegister(emailUser: String, passwordUser: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = getUserUseCase.invoke(emailUser)
            val registerStatus: RegisterStatus
            registerStatus = if (user == null) {
                createUserUseCase.invoke(User(emailUser, passwordUser))
                RegisterSuccess(emailUser)
            } else {
                RegisterError
            }
            //Asynchronous way to notify data changes to the observer
            registerLiveData.postValue(registerStatus)
        }
    }
}