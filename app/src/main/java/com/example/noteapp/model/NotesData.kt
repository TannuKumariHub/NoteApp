package com.example.noteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class NotesData(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    val title:String,
    val content:String,
    val date:String,
    val color:Int=-1
): Serializable
