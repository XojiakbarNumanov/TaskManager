package com.xojiakbar.taskmanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xojiakbar.taskmanager.data.local.entity.TasksCountEntity
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity

@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks  ")
    fun getTasks(): LiveData<MutableList<TasksEntity>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tasks: TasksEntity) : Long

    @Update
    fun update(tasks: TasksEntity)

    @Query("DELETE FROM tasks  where tasks.new_version_code = :new_version_code")
    fun deleteAll(new_version_code:Int)
}