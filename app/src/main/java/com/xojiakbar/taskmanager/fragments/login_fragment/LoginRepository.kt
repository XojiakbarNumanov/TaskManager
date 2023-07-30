package com.xojiakbar.taskmanager.fragments.login_fragment

import android.content.Context
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.ApiService
import com.xojiakbar.taskmanager.data.beans.User
import com.xojiakbar.taskmanager.data.beans.UserBean
import com.xojiakbar.taskmanager.data.local.dao.AppDatabase
import com.xojiakbar.taskmanager.data.local.dao.UsersDao
import com.xojiakbar.taskmanager.data.local.entity.UserEntity

import uz.furorprogress.domain.base.BaseRepository

class LoginRepository(context: Context) : BaseRepository<ApiService>(context) {
    override val apiService: Class<ApiService>?
        get() = apiService
    var usersDao : UsersDao? =null
    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(context)
        usersDao = appDatabase.userDao()

    }
    fun login(login:String,password:String,remember:Boolean,callback: ApiCallback<UserBean>) {
        request(getApi(ApiService::class.java)!!.login(login,password,remember),callback)
    }
    fun putUserToDb(userBean: UserBean): Long {
        val user = UserEntity(
            userBean.user.id,
            userBean.user.login,
            userBean.user.branchs_id,
            userBean.user.created_date,
            userBean.user.description,
            userBean.user.email,
            userBean.user.fio,
            userBean.user.branches_name,
            userBean.user.phone,
            userBean.user.birth_date,
            userBean.user.img_resource_id,
            userBean.user.user_roles_name,
            userBean.user.user_types_id,
            userBean.user.target_id,
            userBean.user.token,
            userBean.user.status,
            userBean.user.user_type_name
        )
        return usersDao!!.insert(user)
    }

}