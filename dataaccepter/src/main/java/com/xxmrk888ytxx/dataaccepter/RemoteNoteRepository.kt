package com.xxmrk888ytxx.dataaccepter

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteNoteRepository(private val context: Context) {


    private val contentResolver by lazy {
        context.contentResolver
    }

    suspend fun queryAllNotes(id: Int?) : List<NoteModel> = withContext(Dispatchers.Default) {
        val cursor = contentResolver.query(baseUri,null,id.toString(),null,null)
            ?: return@withContext emptyList()

        val notesList = mutableListOf<NoteModel>()

        cursor.use {
            while (it.moveToNext()) {
                 notesList.add(
                     NoteModel(
                         id = it.getInt(0),
                         text = it.getString(1)
                     )
                 )
            }
        }

        return@withContext notesList
    }

    suspend fun insertRandomNote() : Uri? {
        val values = ContentValues()
        values.put("id",0)
        values.put("text",System.currentTimeMillis().toString())

        return contentResolver.insert(baseUri,values)
    }

    suspend fun removeNote(id:Int) : Int {
        return contentResolver.delete(baseUri,id.toString(),null)
    }

    suspend fun removeAllNote() : Int {
        return contentResolver.delete(baseUri,"*",null)
    }

    suspend fun updateNote(id:Int) : Int {
        val values = ContentValues()
        values.put("id",id)
        values.put("text",System.currentTimeMillis().toString())
        return contentResolver.update(baseUri,values,null,null)
    }

    companion object {
        val baseUri = "content://com.xxmrk888ytxx.contentprovidersimple.notes".toUri()
    }
}