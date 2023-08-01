package com.xojiakbar.taskmanager.data.repositories

import android.content.Context
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.ApiService
import com.xojiakbar.taskmanager.data.local.database.AppDatabase
import com.xojiakbar.taskmanager.data.local.dao.UsersDao
import okhttp3.ResponseBody
import uz.furorprogress.domain.base.BaseRepository

class ChangePasswordRepository(context: Context) : BaseRepository<ApiService>(context) {
    override val apiService: Class<ApiService>?
        get() = apiService
    var usersDao : UsersDao? =null
    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(context)
        usersDao = appDatabase.userDao()

    }
    fun changePasswor(map : Map<String, String> ,callback: ApiCallback<ResponseBody>) {
        request(getApi(ApiService::class.java).changeUserPassword(map), callback)
    }

}