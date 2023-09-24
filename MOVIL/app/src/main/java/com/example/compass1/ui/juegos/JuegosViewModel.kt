package com.example.compass1.ui.juegos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JuegosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Implementacion de Matches"
    }
    val text: LiveData<String> = _text
}