package com.xojiakbar.taskmanager.fragments.login_fragment

import com.xojiakbar.taskmanager.Utils.BaseRouter

interface LoginRouter : BaseRouter<Any> {
    fun checkLogin(username: String?, password: String?,remember:Boolean)
    fun hideKeyboard()
}