package com.example.youtube.database

import android.app.Application
import androidx.room.Room

class App: Application() {
    private var database: Database? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, Database::class.java, "user")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun getDatabase(): Database? {
        return database
    }

    companion object {
        lateinit var instance: App
    }
}