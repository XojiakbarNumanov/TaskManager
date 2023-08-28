package com.xojiakbar.taskmanager.fragments.tasks_for_admin.dialog

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.beans.task_bean.Task
import com.xojiakbar.taskmanager.data.local.entity.ExecutorsEntity
import com.xojiakbar.taskmanager.data.repositories.TasksDBRepository
import com.xojiakbar.taskmanager.data.repositories.TasksRepository
import okhttp3.ResponseBody

class SetExecutorViewModel (application: Application) : BaseViewModel<SetExecutorRouter>(application) {
    private var repository: TasksRepository? = null
    private var repositoryDB: TasksDBRepository? = null

    init {
        repository = TasksRepository(application)
        repositoryDB = TasksDBRepository(application)
    }
    fun getExecutors() : LiveData<MutableList<ExecutorsEntity>>{
        return repositoryDB?.getExecutors()!!
    }
    fun attachExecutor(row: Task) {
        repository?.attachExecutor(row,object : ApiCallback<ResponseBody> {
            override fun onSuccess(response: ResponseBody) {
                Toast.makeText(application, "success", Toast.LENGTH_SHORT).show()
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                Toast.makeText(application, errorMsg.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}