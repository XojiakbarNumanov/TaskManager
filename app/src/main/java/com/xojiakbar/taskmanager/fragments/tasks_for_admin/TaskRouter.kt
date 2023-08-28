package com.xojiakbar.taskmanager.fragments.tasks_for_admin

import com.xojiakbar.taskmanager.Utils.BaseRouter
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity

interface TaskRouter : BaseRouter<Any> {
    fun showDialog(entity: TasksEntity)
}