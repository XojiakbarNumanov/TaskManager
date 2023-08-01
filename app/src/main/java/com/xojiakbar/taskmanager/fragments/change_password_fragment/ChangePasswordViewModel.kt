package com.xojiakbar.taskmanager.fragments.change_password_fragment

import android.app.Application
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.Utils.Utils
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.repositories.ChangePasswordRepository
import okhttp3.ResponseBody

class ChangePasswordViewModel(application: Application) : BaseViewModel<ChangePasswordRouter>(application) {
    private var changePasswordRepostory: ChangePasswordRepository? = null



    init {
        changePasswordRepostory = ChangePasswordRepository(application)
    }


    fun changeUserPassword(newPassword: String) {
        val map: MutableMap<String, String> = HashMap()
        map["old_password"] = Preferences.getUserPassword()
        map["password"] = newPassword
        if (router != null) {
            router!!.setLoading()
            changePasswordRepostory?.changePasswor(map, object : ApiCallback<ResponseBody> {
                override fun onSuccess(response: ResponseBody) {
                    if (router != null) {
                        Preferences.setUserPassword(newPassword)
                        Preferences.setUserPasswordHash(Utils.getStringDigest(newPassword))
                        router!!.onSuccess(response)
                    }
                }

                override fun onError(throwable: Throwable) {
                    TODO("Not yet implemented")
                }


                override fun onErrorMsg(errorMsg: ErrorResult) {
                    if (router != null) {
                        router!!.onError(errorMsg)
                    }
                }
            })
        }
    }
}