package com.xojiakbar.taskmanager.fragments.forgot_password

import android.app.Application
import com.xojiakbar.taskmanager.Utils.BaseViewModel
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.Utils.Utils
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.repositories.ChangePasswordRepository
import com.xojiakbar.taskmanager.data.repositories.LoginRepository
import okhttp3.ResponseBody

class ForgotPassViewModel(application: Application) : BaseViewModel<ForgotPassRouter>(application) {
    private var repository: LoginRepository? = null



    init {
        repository = LoginRepository(application)
    }
    fun forgotPassword(login:String,newPassword: String) {
        val map: MutableMap<String, String> = HashMap()
        map["login"] = login
        map["password"] = newPassword
        if (router != null) {
            router!!.setLoading("Forgot Password")
            repository?.forgotPass(map, object : ApiCallback<ResponseBody> {
                override fun onSuccess(response: ResponseBody) {
                    if (router != null) {
                        router!!.onSuccess(response)
                    }
                }

                override fun onError(throwable: Throwable) {
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