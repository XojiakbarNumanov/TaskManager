package com.xojiakbar.taskmanager.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xojiakbar.taskmanager.data.local.entity.ExecutorsEntity
import com.xojiakbar.taskmanager.data.local.entity.LineChartEntity
import java.util.concurrent.Executor

@Dao
interface ExecutorsDao {
    @Query("SELECT * FROM executors ")
    fun getExecutors(): LiveData<MutableList<ExecutorsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(executor: ExecutorsEntity) : Long

    @Update
    fun update(executor: ExecutorsEntity)

    @Query("DELETE FROM executors")
    fun deleteAll()

}