package com.xojiakbar.taskmanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xojiakbar.taskmanager.data.local.entity.ProjectGroupsEntity

@Dao
interface ProjectGrDao {
    @Query("SELECT * FROM project_groups")
    fun getProjectGr(): LiveData<MutableList<ProjectGroupsEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(groups: ProjectGroupsEntity): Long
    @Query("DELETE FROM project_groups")
    fun deleteAll()
}