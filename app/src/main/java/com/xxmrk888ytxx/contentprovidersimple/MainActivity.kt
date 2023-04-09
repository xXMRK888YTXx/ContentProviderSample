package com.xxmrk888ytxx.contentprovidersimple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.xxmrk888ytxx.contentprovidersimple.Database.NoteEntity
import com.xxmrk888ytxx.contentprovidersimple.ui.theme.ContentProviderSimpleTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private val db by lazy {
        (this@MainActivity.applicationContext as App).db
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val notes = db.noteDao.getAllNotesFlow().collectAsState(listOf())

            LazyColumn(modifier = Modifier.fillMaxWidth()) {

                item {
                    Button(onClick = {
                        lifecycleScope.launch(Dispatchers.IO) {
                            db.noteDao.insertNote(
                                NoteEntity(
                                    id = 0,
                                    text = System.currentTimeMillis().toString()
                                )
                            )
                        }
                    }) {
                        Text(text = "Add note")
                    }
                }

                items(notes.value) {
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .heightIn(70.dp)) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "id:${it.id}")
                            Text(text = "text:${it.text}")
                        }
                    }
                }
            }
        }
    }
}