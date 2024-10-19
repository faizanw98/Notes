package com.devfaizan.notes.notesview

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.devfaizan.notes.model.Note

@BindingAdapter("noteTitle")
fun TextView.setTitle(item: Note){
    text = item.noteTitle
}
@BindingAdapter("noteText")
fun TextView.setNoteText(item: Note){
    text = item.noteText
}
