package com.xojiakbar.taskmanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xojiakbar.taskmanager.data.local.entity.LineChartEntity
import com.xojiakbar.taskmanager.data.local.entity.ReportTasksEntity
@Dao
interface LChartDao {
    @Query("SELECT * FROM line_chart WHERE day IS NOT NULL ")
    fun getByDay(): LiveData<MutableList<LineChartEntity>>

    @Query("SELECT * FROM line_chart WHERE month IS NOT NULL ")
    fun getByMonth(): LiveData<MutableList<LineChartEntity>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tasks: LineChartEntity) : Long

    @Update
    fun update(tasks: LineChartEntity)

    @Query("DELETE FROM line_chart WHERE month IS NOT NULL")
    fun deleteMonths()
    @Query("DELETE FROM line_chart WHERE day IS NOT NULL")
    fun deleteDays()
}