package com.xojiakbar.taskmanager.fragments.change_password_fragment

import com.xojiakbar.taskmanager.Utils.BaseRouter


interface ChangePasswordRouter : BaseRouter<Any> {
    fun onBack()
    fun changePassword(newPassword: String)
}
