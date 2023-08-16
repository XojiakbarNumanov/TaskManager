package com.xojiakbar.taskmanager.fragments.dashboard_fragment

import android.app.Application
import android.widget.Toast
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.beans.ChartBean.LineChartBean
import com.xojiakbar.taskmanager.data.beans.ChartBean.ProjectGroupBean
import com.xojiakbar.taskmanager.data.beans.report_tasks_bean.ReportTasksBean
import com.xojiakbar.taskmanager.data.beans.task_bean.Task
import com.xojiakbar.taskmanager.data.beans.task_bean.TasksBean
import com.xojiakbar.taskmanager.data.repositories.TasksRepository
import java.text.SimpleDateFormat
import java.util.Date

class DashboardViewModel(application: Application) : BaseViewModel<DashboardRouter>(application) {
    private var repository:TasksRepository ? = null

    init {
        repository = TasksRepository(application)
    }

    fun getNewTasks(userId: Int){
        repository?.getNewTasks(userId,object :ApiCallback<TasksBean>{
            override fun onSuccess(response: TasksBean) {
                val tasks : List<Task> = response.tasks.rows
                repository!!.insetTasks(tasks,1)
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                router?.onError(errorMsg)
            }

        })
    }
    fun getEPTasks(userId: Int){
        repository?.getEPTasks(userId,object :ApiCallback<TasksBean>{
            override fun onSuccess(response: TasksBean) {
                val tasks : List<Task> = response.tasks.rows
                repository!!.insetTasks(tasks,2)
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                router?.onError(errorMsg)
            }

        })
    }
    fun getProcessTasks(userId: Int){
        repository?.getProcessTasks(userId,object :ApiCallback<TasksBean>{
            override fun onSuccess(response: TasksBean) {
                val tasks : List<Task> = response.tasks.rows
                repository!!.insetTasks(tasks,3)
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                router?.onError(errorMsg)
            }

        })
    }
    fun getReviewTasks(userId: Int){
        repository?.getReviewTasks(userId,object :ApiCallback<TasksBean>{
            override fun onSuccess(response: TasksBean) {
                val tasks : List<Task> = response.tasks.rows
                repository!!.insetTasks(tasks,4)
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                router?.onError(errorMsg)
            }

        })
    }
    fun getInfoLForchart(date :String){
        repository?.getInfoForLineChart(date,1,object :ApiCallback<LineChartBean>{
            override fun onSuccess(response: LineChartBean) {
                response.rows?.let { repository?.insetInfoLChatr(it) }
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                Toast.makeText(application, errorMsg.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun getProjectGr(date :String){
        repository?.getInfoProjects(date,object :ApiCallback<ProjectGroupBean>{
            override fun onSuccess(response: ProjectGroupBean) {
                repository?.insetPGDB(response.rows!!)
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                Toast.makeText(application, errorMsg.toString(), Toast.LENGTH_SHORT).show()
            }


        })
    }
    fun getReportTasks(fromDate:String, toDate :String)
    {
        router!!.setLoading("Report of tasks")
        repository?.getReportTasks(fromDate,toDate,object : ApiCallback<ReportTasksBean>{
            override fun onSuccess(response: ReportTasksBean) {
                repository?.insertReportTasks(response.rows)
                router!!.onSuccess(response)
                val userId = Preferences.getUserId()
                getNewTasks(userId)
                getEPTasks(userId)
                getProcessTasks(userId)
                getReviewTasks(userId)
                getInfoLForchart(toDate)
                getProjectGr(toDate)
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                router?.onError(errorMsg)
            }

        })
    }
}