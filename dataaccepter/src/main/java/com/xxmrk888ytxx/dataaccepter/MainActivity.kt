package com.xxmrk888ytxx.dataaccepter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.xxmrk888ytxx.dataaccepter.ui.theme.ContentProviderSimpleTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val remoteNoteRepository by lazy {
        RemoteNoteRepository(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column() {
                val idText = rememberSaveable() {
                    mutableStateOf("")
                }

                OutlinedTextField(value = idText.value, placeholder = {
                    Text("Id notes")
                } ,onValueChange = {
                    idText.value = it
                })

                Button(onClick = {
                    lifecycleScope.launch {
                        Log.d("MyLog",remoteNoteRepository.queryAllNotes(null).toString())
                    }
                }) {
                    Text(text = "Output the notes")
                }

                Button(onClick = {
                    lifecycleScope.launch {
                        Log.d("MyLog",remoteNoteRepository.queryAllNotes(idText.value.toInt()).toString())
                    }
                }) {
                    Text(text = "Output the note with the entered id")
                }

                Button(onClick = {
                    lifecycleScope.launch {
                        Log.d("MyLog","Returned uri:${remoteNoteRepository.insertRandomNote().toString()}")
                    }
                }) {
                    Text(text = "Add random notes")
                }

                Button(onClick = {
                    lifecycleScope.launch {
                        Log.d("MyLog","Returned result:${remoteNoteRepository.removeNote(idText.value.toInt())}")
                    }
                }) {
                    Text(text = "Delete the note with the entered id")
                }

                Button(onClick = {
                    lifecycleScope.launch {
                        Log.d("MyLog","Returned result:${remoteNoteRepository.removeAllNote()}")
                    }
                }) {
                    Text(text = "Delete all notes")
                }

                Button(onClick = {
                    lifecycleScope.launch {
                        Log.d("MyLog","Returned result:${remoteNoteRepository.updateNote(idText.value.toInt())}")
                    }
                }) {
                    Text(text = "Update the note with the id you entered")
                }
            }
        }
    }
}