package com.example.youtube.database

import androidx.room.*
import com.example.youtube.database.models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE id = 1")
    fun getUser(): User

    @Insert
    fun insert(vararg user: User)

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)
}