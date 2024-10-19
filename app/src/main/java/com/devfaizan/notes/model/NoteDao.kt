package com.devfaizan.notes.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note): Long

    @Update
    fun update(note:Note)

    @Query("SELECT * from notes_table WHERE noteId = :key")
    fun get(key: Long): Note

    @Query("DELETE FROM notes_table")
    fun clear()
    @Query("DELETE FROM notes_table WHERE noteId = :key")
    fun remove(key: Long)

    @Query("SELECT * from notes_table WHERE noteId = :key")
    fun getNoteWithId(key: Long): Note

    @Query("SELECT * from notes_table")
    fun getAllNotes(): LiveData<List<Note>>

}