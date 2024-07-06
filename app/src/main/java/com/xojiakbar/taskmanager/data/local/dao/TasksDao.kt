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
    @Query("SELECT * FROM tasks WHERE curr_executor_id = :userId ORDER BY created_date desc")
    fun getTasks(userId: Int?): LiveData<MutableList<TasksEntity>>

    @Query("SELECT * FROM tasks ORDER BY created_date desc")
    fun getAllTasks(): LiveData<MutableList<TasksEntity>>

    @Query("SELECT * FROM tasks where task_statuses_id = 1 ORDER BY created_date desc")
    fun getNewTasks(): LiveData<MutableList<TasksEntity>>

    @Query("SELECT * FROM tasks WHERE task_statuses_id in (2,3,4,5,7) ORDER BY created_date desc")
    fun getProcessTasks(): LiveData<MutableList<TasksEntity>>

    @Query("SELECT * FROM tasks WHERE task_statuses_id = 6 ORDER BY created_date desc")
    fun getreviewTasks(): LiveData<MutableList<TasksEntity>>

    @Query("SELECT * FROM tasks WHERE tasks.task_statuses_id = :status_id AND curr_executor_id = :userId ORDER BY created_date desc")
    fun getTasksById(status_id: Int, userId: Int?): LiveData<MutableList<TasksEntity>>

    @Query("SELECT * FROM tasks WHERE tasks.id = :id ORDER BY created_date desc")
    fun getById(id: Int): LiveData<TasksEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tasks: TasksEntity): Long

    @Update
    fun update(tasks: TasksEntity)

    @Query("UPDATE tasks SET task_statuses_id  = :status_id  WHERE id = :id")
    fun updateStatus(status_id: Int, id: Int)

    @Query("DELETE FROM tasks  where tasks.task_statuses_id = 1 or task_statuses_id = 0 ")
    fun deleteNewTasks()

    @Query("DELETE FROM tasks  where id = :id ")
    fun deleteTaskById(id: Int)

    @Query("DELETE FROM tasks  where tasks.task_statuses_id > 1 ")
    fun deleteTasks()

    @Query("DELETE FROM tasks")
    fun deleteAll()
}