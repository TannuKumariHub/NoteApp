package com.example.noteapp.db

import android.app.Application
import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteapp.model.NotesData

@Database(

    entities = [NotesData::class],
    version = 1,
    exportSchema = false)


abstract class NoteDatabase:RoomDatabase() {

    abstract fun getNoteDao():DAO

    companion object{
        @Volatile
        private var INSTANCE :NoteDatabase?=null

        fun getDatabase(context: Context):NoteDatabase{
            if (INSTANCE==null){
                synchronized(this){
                    INSTANCE= Room.databaseBuilder(context.applicationContext,NoteDatabase::class.java,"note_database").build()

                }

            }
            return INSTANCE!!

        }

    }

}