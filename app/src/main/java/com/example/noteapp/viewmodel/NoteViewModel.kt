package com.example.noteapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.model.NotesData
import com.example.noteapp.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository): ViewModel(){
    fun saveNote(newNote:NotesData)=viewModelScope.launch(Dispatchers.IO){
        repository.addNote(newNote)
    }

    fun updateNote(existingNote:NotesData)=viewModelScope.launch(Dispatchers.IO){
        repository.updateNote(existingNote)

    }

    fun deleteNote(existingNote:NotesData)=viewModelScope.launch(Dispatchers.IO){
        repository.deleteNote(existingNote)
    }
    fun searchNote(query:String): LiveData<List<NotesData>> {
        return repository.searchNote(query)
    }

    fun getAllNotes(): LiveData<List<NotesData>> =repository.getAllNotes()






}