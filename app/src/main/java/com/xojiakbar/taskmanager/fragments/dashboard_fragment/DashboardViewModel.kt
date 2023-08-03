package com.xojiakbar.taskmanager.fragments.dashboard_fragment

import android.app.Application
import android.widget.Toast
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.beans.report_tasks_bean.ReportTasksBean
import com.xojiakbar.taskmanager.data.beans.task_bean.Row
import com.xojiakbar.taskmanager.data.beans.task_bean.TasksBean
import com.xojiakbar.taskmanager.data.beans.user_bean.dashboar_task_bean.StatusTask
import com.xojiakbar.taskmanager.data.repositories.TasksRepository
import okhttp3.ResponseBody

class DashboardViewModel(application: Application) : BaseViewModel<DashboardRouter>(application) {
    private var repository:TasksRepository ? = null

    init {
        repository = TasksRepository(application)
    }
    fun getTasksCnt(userId : Int)
    {
        router!!.setLoading()
        repository?.getTasksCnt(userId, object : ApiCallback<List<StatusTask>>{
            override fun onSuccess(response: List<StatusTask>) {
                repository!!.insertTasksCount(response)
                router!!.onSuccess(response)
                getNewTasks(userId)
                getEPTasks(userId)
                getProcessTasks(userId)
                getReviewTasks(userId)
                getReportTasks("01.08.2023","03.08.2023")
            }
            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                router!!.onError(errorMsg)
            }
        })
    }
    fun getNewTasks(userId: Int){
        repository?.getNewTasks(userId,object :ApiCallback<TasksBean>{
            override fun onSuccess(response: TasksBean) {
                val tasks : List<Row> = response.tasks.rows
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
                val tasks : List<Row> = response.tasks.rows
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
                val tasks : List<Row> = response.tasks.rows
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
                val tasks : List<Row> = response.tasks.rows
                repository!!.insetTasks(tasks,4)
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                router?.onError(errorMsg)
            }

        })
    }
    fun getReportTasks(fromDate:String, toDate :String)
    {
        repository?.getReportTasks(fromDate,toDate,object : ApiCallback<ReportTasksBean>{
            override fun onSuccess(response: ReportTasksBean) {
                repository?.insertReportTasks(response.rows)
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                router?.onError(errorMsg)
            }

        })
    }
}