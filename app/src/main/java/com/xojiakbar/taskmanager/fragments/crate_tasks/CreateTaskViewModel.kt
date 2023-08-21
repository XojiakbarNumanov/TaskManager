package com.xojiakbar.taskmanager.fragments.crate_tasks

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.beans.task_bean.Task
import com.xojiakbar.taskmanager.data.beans.tasks_group.TaskTypesBean
import com.xojiakbar.taskmanager.data.local.entity.DashboardProjectGroupEntity
import com.xojiakbar.taskmanager.data.local.entity.LineChartEntity
import com.xojiakbar.taskmanager.data.local.entity.ProjectGroupsEntity
import com.xojiakbar.taskmanager.data.local.entity.ProjectsEntity
import com.xojiakbar.taskmanager.data.local.entity.ReportTasksEntity
import com.xojiakbar.taskmanager.data.local.entity.TaskGroupEntity
import com.xojiakbar.taskmanager.data.local.entity.TaskTypesEntity
import com.xojiakbar.taskmanager.data.repositories.TasksDBRepository
import com.xojiakbar.taskmanager.data.repositories.TasksRepository
import com.xojiakbar.taskmanager.fragments.home_fragment.HomeRouter
import okhttp3.Response
import okhttp3.ResponseBody

class CreateTaskViewModel (application: Application) : BaseViewModel<HomeRouter>(application) {
    private var repositoryDb: TasksDBRepository? = null
    private var repository: TasksRepository? = null

    init {
        repositoryDb = TasksDBRepository(application)
        repository = TasksRepository(application)
    }


    fun getProjectGr(): LiveData<MutableList<ProjectGroupsEntity>>? {
        return repositoryDb?.getProjectGroups()
    }
    fun getProject(projectGroupID : Int): LiveData<MutableList<ProjectsEntity>>? {
        return repositoryDb?.getProject(projectGroupID)
    }
    fun getTaskGrName(projectID : Int): LiveData<MutableList<TaskGroupEntity>>? {
        return repositoryDb?.getTaskGrName(projectID)
    }
    fun gettaskType(): LiveData<MutableList<TaskTypesEntity>>? {
        return repositoryDb?.getTaskTypes()
    }
    fun createTask(task: Task) {
        repository?.createTask(task,object : ApiCallback<ResponseBody> {
            override fun onSuccess(response: ResponseBody) {
                Toast.makeText(application, "success", Toast.LENGTH_SHORT).show()
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                Toast.makeText(application, errorMsg.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}