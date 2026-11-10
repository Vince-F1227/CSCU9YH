package com.example.noteapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private val _value = MutableLiveData<String>()
    private val _date = MutableLiveData<String>()

    val value: MutableLiveData<String>
        get() = _value

    val date: MutableLiveData<String>
        get() = _date

    init {
        _value.value = "default"
    }

    fun saveDate(selectedDate: String) {
        _date.value = selectedDate
    }
}