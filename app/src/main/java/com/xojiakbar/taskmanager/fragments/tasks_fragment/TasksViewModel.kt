package com.xojiakbar.taskmanager.fragments.tasks_fragment

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.beans.task_bean.Task
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.data.repositories.TasksRepository
import com.xojiakbar.taskmanager.fragments.home_fragment.HomeRouter
import okhttp3.ResponseBody

class TasksViewModel(application: Application) : BaseViewModel<TasksRouter>(application) {
    private var repository: TasksRepository? = null

    init {
        repository = TasksRepository(application)
    }

    fun getTasks(): LiveData<MutableList<TasksEntity>> {
        return repository?.getTasksDB()!!
    }

    fun gettasksById(statusId: Int): LiveData<MutableList<TasksEntity>> {
        return repository?.getTasksByIdDB(statusId)!!
    }

    fun updateTaskStatus(row: Task) {
        router?.setLoading("Update Status")
        repository?.putTaskStatus(row, object : ApiCallback<ResponseBody> {
            override fun onSuccess(response: ResponseBody) {
                router?.onSuccess(response)
                updateTask(row.task_statuses_id!!, row.id!!)

            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                Toast.makeText(application, errorMsg.message, Toast.LENGTH_SHORT).show()
                router?.onError(errorMsg)
            }

        })
    }

    fun updateTask(statusId: Int, id: Int) {
        repository?.updateTasksDB(statusId, id)
    }
}