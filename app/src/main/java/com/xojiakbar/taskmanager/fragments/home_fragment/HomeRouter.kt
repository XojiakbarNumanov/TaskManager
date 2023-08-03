package com.xojiakbar.taskmanager.fragments.home_fragment

import com.xojiakbar.taskmanager.Utils.BaseRouter

interface HomeRouter :BaseRouter<Any> {
    fun changeStatus(id: Int?)
}