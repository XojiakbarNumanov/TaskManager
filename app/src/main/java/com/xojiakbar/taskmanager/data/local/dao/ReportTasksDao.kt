package com.xojiakbar.taskmanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xojiakbar.taskmanager.data.local.entity.ReportTasksEntity

@Dao
interface ReportTasksDao {
    @Query("SELECT * FROM report_tasks order by report_tasks.exec_progress Desc")
    fun getReport(): LiveData<MutableList<ReportTasksEntity>>
    @Query("SELECT * FROM report_tasks WHERE id = :id")
    fun getById(id:Int): LiveData<ReportTasksEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tasks: ReportTasksEntity) : Long

    @Update
    fun update(tasks: ReportTasksEntity)

    @Query("DELETE FROM report_tasks")
    fun deleteAll()
}