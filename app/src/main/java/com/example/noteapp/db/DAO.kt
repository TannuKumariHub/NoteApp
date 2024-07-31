package com.example.noteapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.noteapp.model.NotesData

@Dao

interface DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(notesData: NotesData)

    @Update
    suspend fun updateNote(notesData: NotesData)

    @Query("SELECT * FROM NotesData ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<NotesData>>

    @Query("SELECT * FROM NotesData WHERE title LIKE :query OR content LIKE :query OR date LIKE :query ORDER BY id DESC")
    fun searchNote(query: String): LiveData<List<NotesData>>

    @Delete
    suspend fun deleteNote(notesData: NotesData)

}