package com.xojiakbar.taskmanager.data.repositories

import android.content.Context
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.ApiService
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import uz.furorprogress.domain.base.BaseRepository
import java.io.File

class TasksRepository(context: Context) : BaseRepository<ApiService>(context) {
    override val apiService: Class<ApiService>?
        get() = apiService


    fun putTaskStatus(row: Task, callback: ApiCallback<ResponseBody>) {
        request(getApi(ApiService::class.java).updateTaskStatus(row), callback)
    }

    fun getTasks(userID: Int?, callback: ApiCallback<TasksBean>) {
        request(
            getApi(ApiService::class.java).getTasks(
                userID,
                2,
                Preferences.getIsManager()
            ), callback
        )
    }
    fun getNewTasks(userID: Int?, callback: ApiCallback<TasksBean>) {
        request(
            getApi(ApiService::class.java).getNewTasks(
                userID,
                1,
                2
            ), callback
        )
    }



    fun getReportTasks(fromDate: String, toDate: String, callback: ApiCallback<ReportTasksBean>) {
        request(getApi(ApiService::class.java).getReportTasks(fromDate, toDate), callback)
    }


    fun uploadFileResource(file: File, callback: ApiCallback<Int>) {
        val requestFile: RequestBody = RequestBody.create(
            "file".toMediaTypeOrNull(),
            file

        )
        val inFile: MultipartBody.Part = MultipartBody.Part.createFormData(
            "file",
            file.name, requestFile
        )
        val resourceTypesId: MultipartBody.Part =
            MultipartBody.Part.createFormData("resource_types_id", "7")
        request(getApi(ApiService::class.java).sendFile(inFile, resourceTypesId), callback)
    }
    fun getInfoForLineChart(data: String, isByDate: Int, callback: ApiCallback<LineChartBean>) {
        request(getApi(ApiService::class.java).getInfoForChart(data, isByDate), callback)
    }
    fun getInfoProjects(data: String, callback: ApiCallback<DashboardProjectGroupBean>) {
        request(getApi(ApiService::class.java).getDashProjectGroup(data), callback)
    }
    fun getTasksGroup(callback: ApiCallback<TasksGrBean>) {
        request(getApi(ApiService::class.java).getTasksGroup(), callback)
    }
    fun getExecutors(callback: ApiCallback<List<User>>) {
        request(getApi(ApiService::class.java).getExecutors(1), callback)
    }
    fun getProjects(callback: ApiCallback<ProjectsBean>) {
        request(getApi(ApiService::class.java).getProjects(1), callback)
    }
    fun getProjectGR(callback: ApiCallback<ProjectGroupsBean>) {
        request(getApi(ApiService::class.java).getProjectGroups(1), callback)
    }
    fun getTaskTypes(callback: ApiCallback<TaskTypesBean>) {
        request(getApi(ApiService::class.java).getTaskTayps(), callback)
    }
    fun createTask(task: Task,callback: ApiCallback<ResponseBody>) {
        request(getApi(ApiService::class.java).createTask(task), callback)
    }
}