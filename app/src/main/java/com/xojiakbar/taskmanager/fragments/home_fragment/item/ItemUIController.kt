package com.xojiakbar.taskmanager.fragments.home_fragment.item

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.xojiakbar.taskmanager.BR
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity

import com.xojiakbar.taskmanager.fragments.home_fragment.HomeRouter

class ItemUIController(val context: Context) :BaseObservable(){
    var router : HomeRouter? =null
    var entity: TasksEntity? = null

    fun setEntity99(entity: TasksEntity?) {
        this.entity = entity
        notifyPropertyChanged(BR._all)
    }

    @Bindable
    fun getId() :String{
        return if (entity!=null && entity?.task_code!=null) entity?.task_code.toString() else "---"
    }
}