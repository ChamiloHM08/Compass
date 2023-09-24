package com.example.compass1.ui.juegos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JuegosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Juegos Fragment"
    }
    val text: LiveData<String> = _text
}