package com.xojiakbar.taskmanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xojiakbar.taskmanager.data.local.entity.DashboardProjectGroupEntity

@Dao
interface ProjectsDao {
    @Query("SELECT * FROM dashboard_project_groups ORDER BY tasks_cnt desc")
    fun getProjects(): LiveData<MutableList<DashboardProjectGroupEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tasks: DashboardProjectGroupEntity) : Long

    @Update
    fun update(tasks: DashboardProjectGroupEntity)

    @Query("DELETE FROM dashboard_project_groups")
    fun deleteAll()
}