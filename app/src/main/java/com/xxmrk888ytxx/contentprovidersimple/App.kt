package com.xxmrk888ytxx.contentprovidersimple

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.xxmrk888ytxx.contentprovidersimple.Database.AppDatabase

class App : Application() {

    val db by lazy {
        Room.databaseBuilder(this@App,AppDatabase::class.java,"db.db")
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MyLog","App:OnCreate")
    }
}