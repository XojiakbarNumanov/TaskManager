package com.xojiakbar.taskmanager.fragments.home_fragment

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.beans.task_bean.Task
import com.xojiakbar.taskmanager.data.local.entity.LineChartEntity
import com.xojiakbar.taskmanager.data.local.entity.ProjectGroupEntity
import com.xojiakbar.taskmanager.data.local.entity.ReportTasksEntity
import com.xojiakbar.taskmanager.data.local.entity.TasksCountEntity
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.data.repositories.TasksRepository
import okhttp3.ResponseBody

class HomeViewModel(application: Application) : BaseViewModel<HomeRouter>(application) {
    private var repository: TasksRepository? = null

    init {
        repository = TasksRepository(application)
    }
    fun getInfoForLineChart(): LiveData<MutableList<LineChartEntity>>? {
        return repository?.getLineChartInfoByDay()
    }
    fun getInfoForPieChart(): LiveData<ReportTasksEntity>? {
        return repository?.getRTById(Preferences.getUserId())
    }

    fun getProjectGr(): LiveData<MutableList<ProjectGroupEntity>>? {
        return repository?.getPGDB()
    }

}