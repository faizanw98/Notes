package com.devfaizan.notes.notesview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devfaizan.notes.model.NoteDao

class NotesViewModelFactory(private val dataSource: NoteDao, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesFragmentViewModel::class.java)){
            return NotesFragmentViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}