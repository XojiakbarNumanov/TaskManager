package com.xojiakbar.taskmanager.fragments.login_fragment

import android.app.Application
import androidx.lifecycle.ViewModel
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.Utils.Utils


class LoginViewModel(application: Application) : BaseViewModel(application) {
    private var loginRepository: LoginRepository? = null

    init {
        loginRepository = LoginRepository(application)
    }

    fun login(username: String, password: String, remember: Boolean) {
        loginRepository?.login(
            username,
            password,
            remember,
            { response ->
                Preferences.setUserName(username)
                Preferences.setUserFIO(response.user.fio)
                Preferences.setUserPassword(password)
                Preferences.setUserPasswordHash(Utils.getStringDigest(password))

            }
        ) { error ->

        }

    }
}
