package com.xojiakbar.taskmanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xojiakbar.taskmanager.data.local.entity.LineChartEntity
import com.xojiakbar.taskmanager.data.local.entity.ProjectGroupEntity
import com.xojiakbar.taskmanager.data.local.entity.ReportTasksEntity
@Dao
interface ProjectsDao {
    @Query("SELECT * FROM projects ORDER BY tasks_cnt desc")
    fun getProjects(): LiveData<MutableList<ProjectGroupEntity>>




    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tasks: ProjectGroupEntity) : Long

    @Update
    fun update(tasks: ProjectGroupEntity)

    @Query("DELETE FROM projects")
    fun deleteAll()
}