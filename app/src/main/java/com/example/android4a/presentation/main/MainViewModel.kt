package com.example.android4a.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android4a.domain.entity.User
import com.example.android4a.domain.usecase.CreateUserUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val createUserUseCase: CreateUserUseCase) : ViewModel(){

    val text: MutableLiveData<String> = MutableLiveData()
    val counter:MutableLiveData<Int> = MutableLiveData()

    init {

        counter.value = 0
    }

    fun onClickedIncrement(emailUser: String) {
        viewModelScope.launch {
            createUserUseCase.invoke(User(emailUser))
        }
    }
}