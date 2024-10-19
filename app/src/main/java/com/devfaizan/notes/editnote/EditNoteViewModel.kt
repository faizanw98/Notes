package com.devfaizan.notes.editnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devfaizan.notes.model.Note
import com.devfaizan.notes.model.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditNoteViewModel(dataSource: NoteDao, noteId: Long):ViewModel() {
    private val database = dataSource
    private var _note: MutableLiveData<Note> = MutableLiveData(Note())
    val note: LiveData<Note>
        get() = _note
    init {
        if (noteId==-1L){
            _note.value = Note()
        }else{
            viewModelScope.launch {
                _note.value = loadNote(noteId)
            }
        }
    }
    fun update(note: Note){
        viewModelScope.launch {
            updateNote(note)
        }
    }
    private suspend fun updateNote(note:Note){
            withContext(Dispatchers.IO){
                database.update(note)

        }
    }
    fun insert(note: Note):Long{
        var id: Long =-1L
        viewModelScope.launch {
            id =insertNote(note)
        }
        return id
    }
    private suspend fun insertNote(note: Note):Long {
        var id: Long
        withContext(Dispatchers.IO){
            id =database.insert(note)
        }
        return id
    }

    private suspend fun loadNote(noteId: Long):Note{
        val note: Note
        withContext(Dispatchers.IO){
            note = database.get(noteId)
        }
        return note
    }
}