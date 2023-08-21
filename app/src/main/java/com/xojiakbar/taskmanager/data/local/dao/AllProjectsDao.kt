package com.xojiakbar.taskmanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xojiakbar.taskmanager.data.local.entity.ProjectsEntity

@Dao
interface AllProjectsDao {
    @Query("SELECT * FROM projects Where project_groups_id = :projectGroupsId ")
    fun getProjects(projectGroupsId:Int): LiveData<MutableList<ProjectsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tasks: ProjectsEntity) : Long

    @Update
    fun update(tasks: ProjectsEntity)

    @Query("DELETE FROM projects")
    fun deleteAll()
}