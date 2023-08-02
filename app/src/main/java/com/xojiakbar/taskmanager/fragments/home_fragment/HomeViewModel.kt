package com.xojiakbar.taskmanager.fragments.home_fragment

import android.app.Application
import androidx.lifecycle.LiveData
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.data.local.entity.TasksCountEntity
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.data.repositories.TasksRepository
import com.xojiakbar.taskmanager.fragments.dashboard_fragment.DashboardRouter

class HomeViewModel(application: Application) : BaseViewModel<DashboardRouter>(application) {
    private var repository: TasksRepository? = null

    init {
        repository = TasksRepository(application)
    }

    fun getTasksCnt(): LiveData<TasksCountEntity>{
        return repository?.getTaskCnt()!!
    }
    fun getTasks() :LiveData<MutableList<TasksEntity>>
    {
        return repository?.getTasksDB()!!
    }
}