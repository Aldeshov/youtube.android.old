package com.example.youtube.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.youtube.database.models.LocalUser

@Database(entities = [LocalUser::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun userDao(): UserDao
}