package com.xojiakbar.taskmanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity

@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks  ")
    fun getTasks(): LiveData<MutableList<TasksEntity>>

    @Query("SELECT * FROM tasks WHERE tasks.task_statuses_id = :status_id ")
    fun getTasksById(status_id : Int): LiveData<MutableList<TasksEntity>>
    @Query("SELECT * FROM tasks WHERE tasks.id = :id")
    fun getById(id : Int) : LiveData<TasksEntity>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tasks: TasksEntity) : Long

    @Update
    fun update(tasks: TasksEntity)

    @Query( "UPDATE tasks SET task_statuses_id  = :status_id  WHERE id = :id" )
    fun updateStatus(status_id: Int, id: Int)

    @Query("DELETE FROM tasks  where tasks.new_version_code = :new_version_code")
    fun delete(new_version_code:Int)
    @Query("DELETE FROM tasks")
    fun deleteAll()
}