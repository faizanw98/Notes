package com.devfaizan.notes.notesview

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devfaizan.notes.model.Note
import com.devfaizan.notes.model.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NotesFragmentViewModel(dataSource: NoteDao, application: Application): ViewModel() {
    private val database = dataSource
    var notes:LiveData<List<Note>>?
    init {
        notes = database.getAllNotes()
    }
    private suspend fun deleteNote(key: Long){
        withContext(Dispatchers.IO){
            database.remove(key)
        }
    }
    fun delete(key: Long){
        viewModelScope.launch {
            deleteNote(key)
        }
    }
}