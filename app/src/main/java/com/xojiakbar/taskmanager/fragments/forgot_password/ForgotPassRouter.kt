package com.xojiakbar.taskmanager.fragments.forgot_password

import com.xojiakbar.taskmanager.Utils.BaseRouter

interface ForgotPassRouter : BaseRouter<Any> {
    fun changeLogin(username: String, password: String)
}