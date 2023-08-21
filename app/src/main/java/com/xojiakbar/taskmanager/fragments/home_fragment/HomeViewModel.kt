package com.xojiakbar.taskmanager.fragments.home_fragment

import android.app.Application
import androidx.lifecycle.LiveData
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.data.local.entity.LineChartEntity
import com.xojiakbar.taskmanager.data.local.entity.DashboardProjectGroupEntity
import com.xojiakbar.taskmanager.data.local.entity.ReportTasksEntity
import com.xojiakbar.taskmanager.data.repositories.TasksDBRepository

class HomeViewModel(application: Application) : BaseViewModel<HomeRouter>(application) {
    private var repository: TasksDBRepository? = null

    init {
        repository = TasksDBRepository(application)
    }
    fun getInfoForLineChart(isDayly : Boolean ): LiveData<MutableList<LineChartEntity>>? {
        return if (isDayly) repository?.getLineChartInfoByDay()
        else repository?.getLineChartInfoByMonth()
    }
    fun getInfoForPieChart(): LiveData<ReportTasksEntity>? {
        return repository?.getRTById(Preferences.getUserId())
    }

    fun getProjectGr(): LiveData<MutableList<DashboardProjectGroupEntity>>? {
        return repository?.getPGDB()
    }

}