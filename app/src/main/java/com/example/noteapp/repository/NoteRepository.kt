package com.example.noteapp.repository

import com.example.noteapp.db.NoteDatabase
import com.example.noteapp.model.NotesData

class NoteRepository (private val db:NoteDatabase){
    fun getAllNotes() = db.getNoteDao().getAllNotes()

    fun searchNote(query:String) = db.getNoteDao().searchNote(query)

    suspend fun addNote(notesData: NotesData) = db.getNoteDao().addNote(notesData)

    suspend fun updateNote(notesData: NotesData) = db.getNoteDao().updateNote(notesData)

    suspend fun deleteNote(notesData: NotesData) = db.getNoteDao().deleteNote(notesData)




}