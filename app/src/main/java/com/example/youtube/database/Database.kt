package com.example.youtube.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.youtube.database.models.User

@Database(entities = [User::class], version = 2)
abstract class Database: RoomDatabase() {
    abstract fun userDao(): UserDao
}