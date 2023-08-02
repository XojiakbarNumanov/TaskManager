package com.xojiakbar.taskmanager.fragments.home_fragment

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.xojiakbar.taskmanager.data.local.entity.TasksCountEntity

class HomeUIController(context: Context) :BaseObservable() {
    var router : HomeRouter? = null
    var entity : TasksCountEntity? =null



    @Bindable
    fun getNewTasks() : String{
        return if (entity != null) entity?.new_tasks.toString() else "---"
    }
    fun getAcceptedTasks() : String{
        return if (entity != null) entity?.accepted_tasks.toString() else "---"
    }
    fun getDoneTasks() : String{
        return if (entity != null) entity?.done_tasks.toString() else "---"
    }
    fun getProssesTasks() : String{
        return if (entity != null) entity?.prosses_tasks_cnt.toString() else "---"
    }
    fun getReturnedTasks() : String{
        return if (entity != null) entity?.returned_tasks.toString() else "---"
    }
}