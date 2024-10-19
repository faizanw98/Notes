package com.devfaizan.notes

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devfaizan.notes.model.Note
import com.devfaizan.notes.model.NoteDao
import com.devfaizan.notes.model.NoteDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest {
    private lateinit var noteDao: NoteDao
    private lateinit var db: NoteDatabase
    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context,NoteDatabase::class.java)
            .build()
        noteDao = db.noteDao
    }
    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }
@Test
@Throws(Exception::class)
fun insertAndGetNote(){
    val note = Note(1L,"Title","Text")
    noteDao.insert(note)
    val oneNote = noteDao.get(1)
    Log.i("Test",oneNote.toString())
    }
}