package com.xxmrk888ytxx.contentprovidersimple

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.core.text.isDigitsOnly
import com.xxmrk888ytxx.contentprovidersimple.Database.NoteEntity

class Provider : ContentProvider() {

    private val db by lazy {
        (this.context?.applicationContext as App).db
    }

    override fun onCreate(): Boolean {
        Log.d("MyLog","Provider:OnCreate")

        return true
    }

    /**
     * When accessed, it returns [Cursor] which can be used to retrieve notes from the database
     *
     * If selection == null it returns all notes, if not it returns the note with the passed id
     */
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor? {
        return if(selection != null && selection.isDigitsOnly()) {
            db.noteDao.getNoteByIdCursor(selection.toInt())
        } else {
            db.noteDao.getAllNotesCursor()
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    /**
     * When accessed, adds a note to the database
     *
     * returns [uri] of the note when successfully inserted
     */
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if(values == null) return null

        db.noteDao.insertNote(
            NoteEntity(
                id = values.getAsInteger("id"),
                text = values.getAsString("text")
            )
        )

        return "$basePath/${db.noteDao.getLastId()}".toUri()
    }

    /**
     * When accessed, it deletes the note whose id is passed to selection, if "*" is passed to selection
     * then all the notes will be deleted.
     *
     * Returns 0 on success, 1 on error
     */
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {

        if(selection == "*") {

            db.noteDao.deleteAllNote()

            return 0
        }

        if(selection != null && selection.isDigitsOnly()) {
            val id = selection.toInt()

            db.noteDao.deleteNote(id)

            return 0
        }

        return -1
    }

    /**
     * When accessed, updates the note whose id is passed to selection
     *
     * Returns 0 on success, 1 on error
     */
    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int {
        if(values == null) return -1

        db.noteDao.updateNote(
            NoteEntity(
                id = values.getAsInteger("id"),
                text = values.getAsString("text")
            )
        )

        return 0
    }

    companion object {
        const val basePath = "content://com.xxmrk888ytxx.contentprovidersimple.notes"
    }
}