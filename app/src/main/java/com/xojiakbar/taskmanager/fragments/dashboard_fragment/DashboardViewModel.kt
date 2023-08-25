package com.xojiakbar.taskmanager.fragments.dashboard_fragment

import android.app.Application
import android.widget.Toast
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.beans.ChartBean.LineChartBean
import com.xojiakbar.taskmanager.data.beans.ChartBean.DashboardProjectGroupBean
import com.xojiakbar.taskmanager.data.beans.projects_bean.ProjectGroupsBean
import com.xojiakbar.taskmanager.data.beans.projects_bean.ProjectsBean
import com.xojiakbar.taskmanager.data.beans.report_tasks_bean.ReportTasksBean
import com.xojiakbar.taskmanager.data.beans.task_bean.Task
import com.xojiakbar.taskmanager.data.beans.task_bean.TasksBean
import com.xojiakbar.taskmanager.data.beans.tasks_group.TaskTypesBean
import com.xojiakbar.taskmanager.data.beans.tasks_group.TasksGrBean
import com.xojiakbar.taskmanager.data.beans.user_bean.User
import com.xojiakbar.taskmanager.data.repositories.TasksDBRepository
import com.xojiakbar.taskmanager.data.repositories.TasksRepository

class DashboardViewModel(application: Application) : BaseViewModel<DashboardRouter>(application) {
    private var repository: TasksRepository? = null
    private var repositoryDB: TasksDBRepository? = null

    init {
        repository = TasksRepository(application)
        repositoryDB = TasksDBRepository(application)
    }

    fun getReportTasks(fromDate: String, toDate: String) {
        router!!.setLoading("Report of tasks")
        repository?.getReportTasks(fromDate, toDate, object : ApiCallback<ReportTasksBean> {
            override fun onSuccess(response: ReportTasksBean) {
                repositoryDB?.insertReportTasks(response.rows)
                router!!.onSuccess(response)
                var userId: Int?
                if (Preferences.getUserTypesId() == 4)
                    userId = null
                else
                    userId = Preferences.getUserId()

                getTasks(userId)
                getInfoDoneTasksDayly(toDate)
                getInfoDoneTasksMonthly(toDate)
                getProjectGr(toDate)

                if (Preferences.getUserTypesId() == 4) {
                    getExecutors()
                    getTaskGr()
                    getProjects()
                    getTaskTypes()
                    getProjectGr()
                    getNewTasks(userId)
                }
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                router?.onError(errorMsg)
            }
        })
    }

    fun getTasks(userId: Int?) {
        repository?.getTasks(userId, object : ApiCallback<TasksBean> {
            override fun onSuccess(response: TasksBean) {
                val tasks: List<Task> = response.tasks.rows
                repositoryDB!!.insetTasks(tasks,false)
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                router?.onError(errorMsg)
            }
        })
    }
    fun getNewTasks(userId: Int?) {
        repository?.getNewTasks(userId, object : ApiCallback<TasksBean> {
            override fun onSuccess(response: TasksBean) {
                val tasks: List<Task> = response.tasks.rows
                repositoryDB!!.insetTasks(tasks,true)
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                router?.onError(errorMsg)
            }
        })
    }

    fun getInfoDoneTasksDayly(date: String) {
        repository?.getInfoForLineChart(date, 1, object : ApiCallback<LineChartBean> {
            override fun onSuccess(response: LineChartBean) {
                response.rows?.let { repositoryDB?.insetInfoLChatr(it, true) }
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                Toast.makeText(application, errorMsg.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getInfoDoneTasksMonthly(date: String) {
        repository?.getInfoForLineChart(date, 2, object : ApiCallback<LineChartBean> {
            override fun onSuccess(response: LineChartBean) {
                response.rows?.let { repositoryDB?.insetInfoLChatr(it, false) }
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                Toast.makeText(application, errorMsg.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getProjectGr(date: String) {
        repository?.getInfoProjects(date, object : ApiCallback<DashboardProjectGroupBean> {
            override fun onSuccess(response: DashboardProjectGroupBean) {
                repositoryDB?.insetPGDB(response.rows!!)
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                Toast.makeText(application, errorMsg.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getTaskGr() {
        repository?.getTasksGroup(object : ApiCallback<TasksGrBean> {
            override fun onSuccess(response: TasksGrBean) {
                repositoryDB?.insertTaskGr(response.rows)
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                Toast.makeText(application, errorMsg.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getExecutors() {
        repository?.getExecutors(object : ApiCallback<List<User>> {
            override fun onSuccess(response: List<User>) {
                repositoryDB?.insertExecutors(response)
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                Toast.makeText(application, errorMsg.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getProjects() {
        repository?.getProjects(object : ApiCallback<ProjectsBean> {
            override fun onSuccess(response: ProjectsBean) {
                repositoryDB?.insertProjects(response.rows)
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                Toast.makeText(application, errorMsg.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getProjectGr() {
        repository?.getProjectGR(object : ApiCallback<ProjectGroupsBean> {
            override fun onSuccess(response: ProjectGroupsBean) {
                repositoryDB?.insertProjectGroups(response.rows)
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                Toast.makeText(application, errorMsg.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getTaskTypes() {
        repository?.getTaskTypes(object : ApiCallback<TaskTypesBean> {
            override fun onSuccess(response: TaskTypesBean) {
                repositoryDB?.insertTaskTypes(response.rows)
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                Toast.makeText(application, errorMsg.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}