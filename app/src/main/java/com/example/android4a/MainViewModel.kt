package com.example.android4a

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

    val text: MutableLiveData<String> = MutableLiveData()
    val counter:MutableLiveData<Int> = MutableLiveData()

    init {

        counter.value = 0
    }

    fun onClickedIncrement() {
        counter.value = (counter.value ?: 0) + 1

    }

}