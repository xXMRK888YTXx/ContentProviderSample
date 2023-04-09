package com.xxmrk888ytxx.contentprovidersimple.Database

import android.database.Cursor
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query(SELECT_ALL_NOTES_QUERY)
    fun getAllNotes() : List<NoteEntity>

    @Query(SELECT_ALL_NOTES_QUERY)
    fun getAllNotesFlow() : Flow<List<NoteEntity>>

    @Query(SELECT_ALL_NOTES_QUERY)
    fun getAllNotesCursor() : Cursor

    @Query("SELECT * FROM NotesTable WHERE id = :id")
    fun getNoteByIdCursor(id:Int) : Cursor?

    @Insert
    fun insertNote(noteEntity: NoteEntity)

    @Query("DELETE FROM NotesTable WHERE id = :id")
    fun deleteNote(id:Int)

    @Query("DELETE FROM NotesTable")
    fun deleteAllNote()

    @Update
    fun updateNote(noteEntity: NoteEntity)

    @Query("SELECT MAX(id) FROM NotesTable")
    fun getLastId() : Int

    companion object {
        const val SELECT_ALL_NOTES_QUERY = "SELECT * FROM NotesTable"
    }
}