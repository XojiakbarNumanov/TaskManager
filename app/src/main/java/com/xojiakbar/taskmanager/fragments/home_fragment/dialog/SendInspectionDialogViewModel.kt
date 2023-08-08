package com.xojiakbar.taskmanager.fragments.home_fragment.dialog

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.beans.BaseBean
import com.xojiakbar.taskmanager.data.beans.task_bean.Row
import com.xojiakbar.taskmanager.data.repositories.TasksRepository
import okhttp3.ResponseBody
import java.io.File

class SendInspectionDialogViewModel(application: Application) :
    BaseViewModel<SendInspectionDialogRouter>(application) {
    private var repository: TasksRepository? = null
    private lateinit var imgResIds: List<Int>
    var listOfResId = ArrayList<BaseBean>()

    init {
        repository = TasksRepository(application)
    }

    fun uploadResources(files: ArrayList<File>, row: Row, i: Int) {
        if (files.size > 0) {
            if (i < files.size) {
                val file = files[i]
                repository?.uploadFileResource(file, object : ApiCallback<Int> {
                    override fun onSuccess(response: Int) {
                        listOfResId.add(BaseBean())
                        listOfResId[i].id = response
                        Toast.makeText(application, "succes", Toast.LENGTH_SHORT).show()
                        if (i == files.size - 1) {
                            row.file_ids = listOfResId
                            updateTaskStatus(row)
                        }else
                        uploadResources(files, row, i + 1)
                    }
                    override fun onError(throwable: Throwable) {
                    }
                    override fun onErrorMsg(errorMsg: ErrorResult) {
                        Toast.makeText(application, errorMsg.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }
        }
    }
    fun updateTask(statusId: Int,id: Int){
        repository?.updateTasksDB(statusId,id)
    }
    fun updateTaskStatus(row: Row) {
        router?.setLoading("Update Status")
        repository?.putTaskStatus(row, object : ApiCallback<ResponseBody> {
            override fun onSuccess(response: ResponseBody) {
                router?.onSuccess(response)
                updateTask(row.task_statuses_id!!,row.id!!)
            }

            override fun onError(throwable: Throwable) {
            }

            override fun onErrorMsg(errorMsg: ErrorResult) {
                Toast.makeText(application, errorMsg.message, Toast.LENGTH_SHORT).show()
                router?.onError(errorMsg)
            }

        })
    }

}