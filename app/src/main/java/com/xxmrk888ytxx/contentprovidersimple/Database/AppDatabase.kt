package com.xxmrk888ytxx.contentprovidersimple.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        NoteEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract val noteDao:NoteDao
}