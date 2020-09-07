package com.example.android4a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    val mainViewModel : MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //mainViewModel.onStart()
        MainButton.setOnClickListener{
        mainViewModel.onClickedIncrement()
        }
        mainViewModel.text.observe(this, Observer {
            value -> MainText.text = value.toString()
        })


    }
}
