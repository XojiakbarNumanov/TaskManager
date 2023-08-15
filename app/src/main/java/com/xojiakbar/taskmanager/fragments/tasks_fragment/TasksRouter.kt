package com.xojiakbar.taskmanager.fragments.tasks_fragment

import com.xojiakbar.taskmanager.Utils.BaseRouter
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity

interface TasksRouter : BaseRouter<Any>{
    fun changeStatus(id: TasksEntity)
    fun showDialog(task : TasksEntity)
}