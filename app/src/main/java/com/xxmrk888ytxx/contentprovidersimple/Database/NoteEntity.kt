package com.xxmrk888ytxx.contentprovidersimple.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "NotesTable"
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val text:String
)