package com.xojiakbar.taskmanager.fragments.login_fragment

import android.content.Context
import androidx.core.util.Consumer
import com.xojiakbar.taskmanager.api.ApiService
import com.xojiakbar.taskmanager.api.result.ErrorResult

import com.xojiakbar.taskmanager.data.beans.UserBean
import uz.furorprogress.domain.base.BaseRepository

class LoginRepository(context: Context) : BaseRepository<ApiService>(context) {

    fun login(login:String,password:String,remember:Boolean,success : Consumer<UserBean>, error : Consumer<ErrorResult>) {
        request(api!!.login(login,password,remember),success,error)
    }

    override val apiService: Class<ApiService>?
        get() = apiService
}