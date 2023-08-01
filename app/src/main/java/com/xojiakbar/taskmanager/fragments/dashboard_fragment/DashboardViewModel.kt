package com.xojiakbar.taskmanager.fragments.dashboard_fragment

import android.app.Application
import android.widget.Toast
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.beans.user_bean.dashboar_task_bean.DashboardTaskcntBean
import com.xojiakbar.taskmanager.data.repositories.TasksRepository
import okhttp3.ResponseBody

class DashboardViewModel(application: Application) : BaseViewModel<DashboardRouter>(application) {
    private var repository:TasksRepository ? = null

    init {
        repository = TasksRepository(application)
    }
    fun getTasksCnt(userId : Int)
    {
        router?.setLoading()
        repository?.getTasksCnt(userId, object : ApiCallback<DashboardTaskcntBean>{
            override fun onSuccess(response: DashboardTaskcntBean) {
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