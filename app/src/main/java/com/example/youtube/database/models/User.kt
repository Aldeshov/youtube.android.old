package com.example.youtube.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey() var id: Int? = null,
    @ColumnInfo(name = "email") var email: String? = null,
    @ColumnInfo(name = "full_name") var full_name: String? = null,
    @ColumnInfo(name = "avatar") var avatar: String? = null,
    @ColumnInfo(name = "token") var token: String? = null)