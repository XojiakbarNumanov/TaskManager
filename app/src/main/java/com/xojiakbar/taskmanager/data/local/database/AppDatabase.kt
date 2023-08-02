package com.xojiakbar.taskmanager.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.xojiakbar.taskmanager.data.local.dao.TasksDao
import com.xojiakbar.taskmanager.data.local.dao.TaskscntDao
import com.xojiakbar.taskmanager.data.local.dao.UsersDao
import com.xojiakbar.taskmanager.data.local.entity.TasksCountEntity
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class,TasksCountEntity::class,TasksEntity::class],
    version = 1,
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UsersDao
    abstract fun tasksCntDao(): TaskscntDao
    abstract fun taskDao() : TasksDao
    companion object {
        var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class.java) {
                    if (instance == null) {
                        instance = databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "TaskManegerDB"
                        )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return instance!!
        }
    }
}