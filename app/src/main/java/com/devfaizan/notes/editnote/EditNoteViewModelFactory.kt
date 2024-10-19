package com.devfaizan.notes.editnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devfaizan.notes.model.NoteDao

@Suppress("UNCHECKED_CAST")
class EditNoteViewModelFactory(private val dataSource: NoteDao, private val noteId: Long): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditNoteViewModel::class.java)){
            return EditNoteViewModel(dataSource,noteId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}