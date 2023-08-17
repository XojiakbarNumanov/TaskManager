package com.xojiakbar.taskmanager.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.ApiService
import com.xojiakbar.taskmanager.data.beans.ChartBean.LineChartBean
import com.xojiakbar.taskmanager.data.beans.ChartBean.ProjectGroupBean
import com.xojiakbar.taskmanager.data.beans.ChartBean.ProjectGroupRows
import com.xojiakbar.taskmanager.data.beans.ChartBean.Rows
import com.xojiakbar.taskmanager.data.beans.report_tasks_bean.ReportTasksBean
import com.xojiakbar.taskmanager.data.beans.task_bean.Task
import com.xojiakbar.taskmanager.data.beans.task_bean.TasksBean
import com.xojiakbar.taskmanager.data.local.dao.LChartDao
import com.xojiakbar.taskmanager.data.local.dao.ProjectsDao
import com.xojiakbar.taskmanager.data.local.dao.ReportTasksDao
import com.xojiakbar.taskmanager.data.local.dao.TasksDao
import com.xojiakbar.taskmanager.data.local.dao.TaskscntDao
import com.xojiakbar.taskmanager.data.local.database.AppDatabase
import com.xojiakbar.taskmanager.data.local.entity.LineChartEntity
import com.xojiakbar.taskmanager.data.local.entity.ProjectGroupEntity
import com.xojiakbar.taskmanager.data.local.entity.ReportTasksEntity
import com.xojiakbar.taskmanager.data.local.entity.TasksCountEntity
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import uz.furorprogress.domain.base.BaseRepository
import java.io.File

class TasksRepository(context: Context) : BaseRepository<ApiService>(context) {
    override val apiService: Class<ApiService>?
        get() = apiService
    var tasksCntDao: TaskscntDao? = null
    var tasksDao: TasksDao? = null
    var reportDao: ReportTasksDao? = null
    var lineChartDao : LChartDao? = null
    var projectsDao : ProjectsDao? = null

    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(context)
        tasksCntDao = appDatabase.tasksCntDao()
        tasksDao = appDatabase.taskDao()
        reportDao = appDatabase.reportDao()
        lineChartDao = appDatabase.lineDoa()
        projectsDao = appDatabase.projectsDao()
    }

    fun putTaskStatus(row: Task, callback: ApiCallback<ResponseBody>) {
        request(getApi(ApiService::class.java).updateTaskStatus(row), callback)
    }

    fun getTaskCnt(): LiveData<TasksCountEntity>? {
        return tasksCntDao?.getTasks()
    }

    fun getTasksDB(): LiveData<MutableList<TasksEntity>>? {
        return tasksDao?.getTasks()
    }

    fun getTasksByIdDB(statusId: Int): LiveData<MutableList<TasksEntity>>? {
        return tasksDao?.getTasksById(statusId)
    }

    fun updateTasksDB(statusId: Int, id: Int) {
        tasksDao?.updateStatus(statusId, id)
    }

    fun getByIdDB(id: Int): LiveData<TasksEntity> {
        return tasksDao?.getById(id)!!
    }

    fun getNewTasks(userID: Int, callback: ApiCallback<TasksBean>) {
        request(getApi(ApiService::class.java).getNewTasks(userID, 1, 2), callback)
    }

    fun getEPTasks(userID: Int, callback: ApiCallback<TasksBean>) {
        request(
            getApi(ApiService::class.java).getEPTasks(userID, 2, 2, Preferences.getIsManager()),
            callback
        )
    }

    fun getProcessTasks(userID: Int, callback: ApiCallback<TasksBean>) {
        request(
            getApi(ApiService::class.java).getProcessTasks(
                userID,
                3,
                2,
                Preferences.getIsManager()
            ), callback
        )
    }

    fun getReviewTasks(userID: Int, callback: ApiCallback<TasksBean>) {
        request(
            getApi(ApiService::class.java).getEPTasks(userID, 4, 2, Preferences.getIsManager()),
            callback
        )
    }

    fun insetTasks(tasks: List<Task>, newVersioncode: Int) {
        tasksDao?.delete(newVersioncode)
        for (task in tasks) {
            val tasksEntity = TasksEntity()
            tasksEntity.id = task.id
            tasksEntity.task_statuses_id = task.task_statuses_id
            tasksEntity.task_code = task.task_code
            tasksEntity.projects_name = task.projects_name
            tasksEntity.task_priorities_name = task.task_priorities_name
            tasksEntity.name = task.name
            tasksEntity.created_date = task.created_date
            tasksEntity.curr_executor_name = task.curr_executor_name
            tasksEntity.process_time = task.process_time
            tasksEntity.task_priorities_id = task.task_priorities_id
            tasksEntity.status_description = task.status_description
            tasksEntity.task_statuses_name = task.task_statuses_name
            tasksEntity.planned_start_date = task.planned_start_date
            tasksEntity.expired_date = task.expired_date
            tasksEntity.task_types_name = task.task_types_name
            tasksEntity.task_types_id = task.task_types_id
            tasksEntity.created_users_name = task.created_users_name
            tasksEntity.hard_index = task.hard_index
            tasksEntity.time_leave = task.time_leave
            tasksEntity.new_version_code = newVersioncode
            tasksDao?.insert(tasksEntity)
        }

    }

    fun getReportTasks(fromDate: String, toDate: String, callback: ApiCallback<ReportTasksBean>) {
        request(getApi(ApiService::class.java).getReportTasks(fromDate, toDate), callback)
    }

    fun insertReportTasks(reports: List<com.xojiakbar.taskmanager.data.beans.report_tasks_bean.Row>) {
        reportDao?.deleteAll()
        for (report in reports) {

            val reportTasks = ReportTasksEntity(
                report.id,
                report.accepted_tasks_cnt,
                report.accepted_tasks_hard_index,
                report.done_tasks_cnt,
                report.done_tasks_hard_index,
                report.done_tasks_time_leave,
                report.duration,
                report.email,
                report.exec_progress,
                report.hard_index,
                report.img_resource_id,
                report.in_progress_tasks_cnt,
                report.in_progress_tasks_hard_index,
                report.in_progress_tasks_interval,
                report.name,
                report.not_accepted_tasks_cnt,
                report.not_accepted_tasks_hard_index,
                report.not_accepted_tasks_interval,
                report.ordering,
                report.pause_tasks_cnt,
                report.pause_tasks_hard_index,
                report.phone,
                report.progress_interval,
                report.rating,
                report.returned_tasks_cnt,
                report.returned_tasks_hard_index,
                report.rownum
            )
            reportDao?.insert(reportTasks)
        }
    }

    fun getReportTasksFromDB(): LiveData<MutableList<ReportTasksEntity>>? {
        return reportDao?.getReport()
    }
    fun getRTById(userId: Int): LiveData<ReportTasksEntity>? {
        return reportDao?.getById(userId)
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
    //for LineChart
    fun getInfoForLineChart(data:String ,isByDate:Int,callback: ApiCallback<LineChartBean>){
        request(getApi(ApiService::class.java).getInfoForChart(data,isByDate),callback)
    }

    fun getLineChartInfoByDay(): LiveData<MutableList<LineChartEntity>> {
        return lineChartDao?.getByDay()!!
    }
    fun getLineChartInfoByMonth(): LiveData<MutableList<LineChartEntity>> {
        return lineChartDao?.getByMonth()!!
    }
    fun insetInfoLChatr(rows: List<Rows> , isDayly :Boolean) {
        if (isDayly)
            lineChartDao?.deleteDays()
        else
            lineChartDao?.deleteMonths()
        for (row in rows) {
            val chartInfo = LineChartEntity()
            chartInfo.day = row.day
            chartInfo.month = row.month
            chartInfo.tasks_cnt = row.tasks_cnt
            lineChartDao?.insert(chartInfo)
        }

    }
    //for BarChart
    fun getInfoProjects(data:String ,callback: ApiCallback<ProjectGroupBean>){
        request(getApi(ApiService::class.java).getProjectGroup(data),callback)
    }
    fun getPGDB(): LiveData<MutableList<ProjectGroupEntity>> {
        return projectsDao?.getProjects()!!
    }
    fun insetPGDB(rows: List<ProjectGroupRows>) {
        projectsDao?.deleteAll()
        for (row in rows) {
            val projectGr = ProjectGroupEntity()
            projectGr.id = row.id
            projectGr.name = row.name
            projectGr.tasks_cnt = row.tasks_cnt
            projectsDao?.insert(projectGr)
        }

    }
}