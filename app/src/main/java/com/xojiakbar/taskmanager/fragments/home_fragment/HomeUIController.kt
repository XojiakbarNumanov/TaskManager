package com.xojiakbar.taskmanager.fragments.home_fragment

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity

class HomeUIController(context: Context) :BaseObservable() {
    var router : HomeRouter? = null
    var listentity : List<TasksEntity>? =null

    @Bindable
    fun getNewTasks() : String{
        if (listentity!= null) {
        var k = 0
        for (i in listentity!!){
            if (i.task_statuses_id == 1)
                k++
        }
        return  k.toString()
    }
    else return "---"
    }
    fun getAcceptedTasks() : String{
        if (listentity!= null) {
        var k = 0
        for (i in listentity!!){
            if (i.task_statuses_id == 3)
                k++
        }
        return  k.toString()
    }
    else return "---"
    }
    fun getDoneTasks() : String{
        if (listentity!= null) {
        var k = 0
        for (i in listentity!!){
            if (i.task_statuses_id == 8)
                k++
        }
        return  k.toString()
    }
    else return "---"
    }
    fun getProssesTasks() : String{
        if (listentity!=null) {
            var k = 0
            for (i in listentity!!) {
                if (i.task_statuses_id == 4 || i.task_statuses_id == 5)
                    k++
            }
            return k.toString()
        }
        else return "---"
    }
    fun getReturnedTasks() : String{
        if (listentity!= null) {
            var k = 0
            for (i in listentity!!) {
                if (i.task_statuses_id == 7)
                    k++
            }
            return k.toString()
        }
        else return "---"
    }
}