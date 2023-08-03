package com.xojiakbar.taskmanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xojiakbar.taskmanager.data.local.entity.ReportTasksEntity
import com.xojiakbar.taskmanager.data.local.entity.TasksCountEntity

@Dao
interface ReportTasksDao {
    @Query("SELECT * FROM report_tasks order by report_tasks.exec_progress Desc")
    fun getReport(): LiveData<MutableList<ReportTasksEntity>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tasks: ReportTasksEntity) : Long

    @Update
    fun update(tasks: ReportTasksEntity)

    @Query("DELETE FROM report_tasks")
    fun deleteAll()
}