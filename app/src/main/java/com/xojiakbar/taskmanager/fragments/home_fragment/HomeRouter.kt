package com.xojiakbar.taskmanager.fragments.home_fragment

import com.xojiakbar.taskmanager.Utils.BaseRouter
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity

interface HomeRouter :BaseRouter<Any> {
    fun changeStatus(id: TasksEntity)
    fun showDialog(task : TasksEntity)
}