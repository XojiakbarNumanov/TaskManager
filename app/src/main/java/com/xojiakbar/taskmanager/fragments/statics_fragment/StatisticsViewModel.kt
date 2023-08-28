package com.xojiakbar.taskmanager.fragments.statics_fragment

import android.app.Application
import androidx.lifecycle.LiveData
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.data.local.entity.ReportTasksEntity
import com.xojiakbar.taskmanager.data.repositories.TasksDBRepository
import com.xojiakbar.taskmanager.data.repositories.TasksRepository
import okhttp3.ResponseBody

class StatisticsViewModel (application: Application) : BaseViewModel<StatisticsRouter>(application){
        private var repository: TasksRepository? = null
        private var repositoryDB: TasksDBRepository? = null

        init {
            repository = TasksRepository(application)
            repositoryDB = TasksDBRepository(application)
        }


        fun getReportTasks() : LiveData<MutableList<ReportTasksEntity>>
        {
            return repositoryDB?.getReportTasksFromDB()!!
        }
}