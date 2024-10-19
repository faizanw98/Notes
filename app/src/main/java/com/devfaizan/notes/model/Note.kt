package com.devfaizan.notes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0L,
    @ColumnInfo("note_title")
    var noteTitle: String = "",
    @ColumnInfo("note_text")
    var noteText: String = ""
)
