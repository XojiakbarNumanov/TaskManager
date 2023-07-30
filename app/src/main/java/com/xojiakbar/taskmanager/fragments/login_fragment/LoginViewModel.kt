package com.xojiakbar.taskmanager.fragments.login_fragment

import android.app.Application
import android.widget.Toast
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.Utils.Utils
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.beans.UserBean


class LoginViewModel(application: Application) : BaseViewModel<LoginRouter>(application){
    private var loginRepository: LoginRepository? = null



    init {
        loginRepository = LoginRepository(application)
    }

    fun login(username: String, password: String, remember: Boolean) {
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
                    loginRepository!!.putUserToDb(response)
                    router?.onSuccess(response)
                }

                override fun onError(throwable: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onErrorMsg(errorMsg: ErrorResult) {
                }

            }
        )
    }
}
