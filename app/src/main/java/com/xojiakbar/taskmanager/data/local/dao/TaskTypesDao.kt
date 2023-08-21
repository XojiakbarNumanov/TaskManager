package com.xojiakbar.taskmanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xojiakbar.taskmanager.data.local.entity.TaskTypesEntity

@Dao
interface TaskTypesDao {
    @Query("SELECT * FROM task_types")
    fun get(): LiveData<MutableList<TaskTypesEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(types: TaskTypesEntity): Long
    @Query("DELETE FROM task_types")
    fun deleteAll()
}