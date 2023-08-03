package com.xojiakbar.taskmanager.fragments.statics_fragment

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.local.entity.ReportTasksEntity
import com.xojiakbar.taskmanager.data.local.entity.TasksCountEntity
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.data.repositories.TasksRepository
import okhttp3.ResponseBody

class StatisticsViewModel (application: Application) : BaseViewModel<StatisticsRouter>(application){
        private var repository: TasksRepository? = null

        init {
            repository = TasksRepository(application)
        }


        fun getReportTasks() : LiveData<MutableList<ReportTasksEntity>>
        {
            return repository?.getReportTasksFromDB()!!
        }
}