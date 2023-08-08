package com.xojiakbar.taskmanager.fragments.login_fragment

import android.app.Application
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.Utils.Utils
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.beans.user_bean.UserBean
import com.xojiakbar.taskmanager.data.repositories.LoginRepository


class LoginViewModel(application: Application) : BaseViewModel<LoginRouter>(application){
    private var loginRepository: LoginRepository? = null

    init {
        loginRepository = LoginRepository(application)
    }

    fun login(username: String, password: String, remember: Boolean) {
        router!!.setLoading("login")
        loginRepository?.login(
            username,
            password,
            remember,
            object : ApiCallback<UserBean>{
                override fun onSuccess(response: UserBean) {
                    Preferences.setUserName(username)
                    Preferences.setUserFIO(response.user.fio)
                    Preferences.setUserPassword(password)
                    Preferences.setUserPasswordHash(Utils.getStringDigest(password))
                    Preferences.setUserRolesName(response.user.user_roles_name)
                    Preferences.setIsManager(response.user.is_manager)
                    Preferences.setImageResource(response.user.img_resource_id)
                    loginRepository!!.putUserToDb(response)
                    Preferences.setUserId(response.user.id)
                    router?.onSuccess(response)
                }

                override fun onError(throwable: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onErrorMsg(errorMsg: ErrorResult) {
                    router?.onError(errorMsg)
                }

            }
        )
    }
}
