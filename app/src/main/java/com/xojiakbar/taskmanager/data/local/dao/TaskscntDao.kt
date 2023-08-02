package com.xojiakbar.taskmanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xojiakbar.taskmanager.data.local.entity.TasksCountEntity

@Dao
interface TaskscntDao {

        @Query("SELECT * FROM tasksCount limit 1 ")
        fun getTasks():LiveData<TasksCountEntity>


        @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun insert(tasks: TasksCountEntity) : Long

        @Update
        fun update(tasks: TasksCountEntity)

        @Query("DELETE FROM tasksCount")
        fun deleteAll()

}