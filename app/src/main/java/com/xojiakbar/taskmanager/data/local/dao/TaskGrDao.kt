package com.xojiakbar.taskmanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xojiakbar.taskmanager.data.local.entity.TaskGroupEntity

@Dao
interface TaskGrDao {
    @Query("Select * From task_group Where projects_id = :projectId")
    fun getTaskGr(projectId: Int): LiveData<MutableList<TaskGroupEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTaskGr(task: TaskGroupEntity): Long
    @Query("DELETE FROM task_group")
    fun deleteAll()

}