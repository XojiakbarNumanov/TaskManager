package com.xojiakbar.taskmanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xojiakbar.taskmanager.data.local.entity.UserEntity


@Dao
interface UsersDao {
    @Query("SELECT * FROM users limit 1")
    fun getOneUser(): LiveData<UserEntity?>?


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserEntity): Long

    @Update
    fun update(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)
    @Query("DELETE FROM users")
    fun deleteAll()
}