package com.example.noteapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    private val _notes = MutableLiveData<List<String>>(emptyList())
    private val _date = MutableLiveData<String>()

    val notes: MutableLiveData<List<String>>
        get() = _notes

    val date: MutableLiveData<String>
        get() = _date

    fun addNote(note: String) {
        val currentNotes = _notes.value ?: emptyList()
        _notes.value = currentNotes + note
    }

    fun loadNotes(savedNotes: List<String>) {
        _notes.value = savedNotes
    }

    fun saveDate(selectedDate: String) {
        _date.value = selectedDate
    }
}