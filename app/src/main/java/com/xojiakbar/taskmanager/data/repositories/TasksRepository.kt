package com.xojiakbar.taskmanager.data.repositories

import android.content.Context
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.ApiService
import com.xojiakbar.taskmanager.data.beans.user_bean.dashboar_task_bean.DashboardTaskcntBean
import com.xojiakbar.taskmanager.data.local.database.AppDatabase
import com.xojiakbar.taskmanager.data.local.dao.UsersDao
import okhttp3.ResponseBody
import uz.furorprogress.domain.base.BaseRepository

class TasksRepository(context: Context) : BaseRepository<ApiService>(context) {
    override val apiService: Class<ApiService>?
        get() = apiService
    var usersDao : UsersDao? =null
    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(context)
        usersDao = appDatabase.userDao()
    }
    fun getTasksCnt(userID : Int ,callback: ApiCallback<DashboardTaskcntBean>) {
        request(getApi(ApiService::class.java).getTasksCnt(userID), callback)
    }
}