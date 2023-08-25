package com.xojiakbar.taskmanager.fragments.tasks_for_admin

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.beans.task_bean.Task
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.data.repositories.TasksDBRepository
import com.xojiakbar.taskmanager.data.repositories.TasksRepository
import okhttp3.ResponseBody

class TasksViewModel(application: Application) : BaseViewModel<TaskRouter>(application) {
    private var repository: TasksRepository? = null
    private var repositoryDB: TasksDBRepository? = null

    init {
        repository = TasksRepository(application)
        repositoryDB = TasksDBRepository(application)
    }
    fun getTasks(filter:Int): LiveData<MutableList<TasksEntity>> {
        return when {
            filter == 1-> {
                repositoryDB?.getNewTasks()!!
            }
            filter == 2-> {
                repositoryDB?.getprocessTasks()!!
            }
            filter == 3-> {
                repositoryDB?.getreviewTasks()!!
            }
            else -> repositoryDB?.getAllTasks()!!
        }
    }
}