package com.xojiakbar.taskmanager.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.data.local.beans.UserBean
import com.xojiakbar.taskmanager.data.local.dao.UsersDao
import com.xojiakbar.taskmanager.ui.base.BaseRepository

class LoginRepository(application: Application?) : BaseRepository(application!!) {
//    private val usersDao: UsersDao
//
//    init {
//        val appDatabase: AppDatabase = AppDatabase.getInstance(application)
//        usersDao = appDatabase.getUsersDao()
//    }
//
//    fun login(
//        username: String?, password: String?,
//        remember: Boolean, user_type: String?,
//        loaderCallbacks: ApiCallback<UserSettingsBean?>?
//    ) {
//        request(getApi().login(username, password, remember, user_type), loaderCallbacks)
//    }
//
//    fun putUserToDb(userBean: UserBean): Long {
//        val user = Users(
//            userBean.getId(),
//            userBean.getLogin(),
//            userBean.getCreated_date(),
//            userBean.getBranchs_id(),
//            userBean.getBranchs_level(),
//            userBean.getBranchs_name(),
//            userBean.getFio(),
//            userBean.getEmail(),
//            userBean.getPhone(),
//            userBean.getBirth_date(),
//            userBean.getDescription(),
//            userBean.getImg_resource_id(),
//            userBean.getStatus(),
//            userBean.getTarget_id(),
//            userBean.getUser_types_id(),
//            userBean.getUser_type_name(),
//            userBean.getUser_roles_name()
//        )
//        return usersDao.insert(user)
//    }
//
//    val user: LiveData<Any>
//        get() = usersDao.getOneUser()
}
