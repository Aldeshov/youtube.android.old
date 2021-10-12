package com.example.youtube.database

import androidx.room.*
import com.example.youtube.database.models.LocalUser

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE id = 1")
    fun getUser(): LocalUser?

    @Insert
    fun insert(vararg localUser: LocalUser)

    @Delete
    fun delete(localUser: LocalUser)

    @Update
    fun update(localUser: LocalUser)
}