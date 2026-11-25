
package com.example.noteapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Note(
    val text: String,
    val date: String
)

class MyViewModel : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>(emptyList())
    private val _date = MutableLiveData<String>()

    val notes: MutableLiveData<List<Note>>
        get() = _notes

    val date: MutableLiveData<String>
        get() = _date

    fun addNote(note: Note) {
        val currentNotes = _notes.value ?: emptyList()
        _notes.value = currentNotes + note
    }

    fun loadNotes(savedNotes: List<Note>) {
        _notes.value = savedNotes
    }

    fun deleteNote(position: Int) {
        val currentNotes = _notes.value ?: emptyList()

        if (position in currentNotes.indices) {
            _notes.value = currentNotes.filterIndexed { index, _ ->
                index != position
            }
        }
    }

    fun saveDate(selectedDate: String) {
        _date.value = selectedDate
    }
}
