package com.xojiakbar.taskmanager.fragments.statics_fragment.item

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.xojiakbar.taskmanager.BR
import com.xojiakbar.taskmanager.data.local.entity.ReportTasksEntity
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.fragments.statics_fragment.StatisticsRouter

class StatisticsItemUIController (val context: Context) : BaseObservable(){
    var router : StatisticsRouter? =null
    var entity: ReportTasksEntity? = null

    fun setForEntity(entity: ReportTasksEntity?) {
        this.entity = entity
        notifyPropertyChanged(BR._all)
    }
    @Bindable
    fun getName() :String{
        return if (entity!=null && entity?.name!=null) entity?.name.toString() else "---"
    }
    @Bindable
    fun getScore() :String {
        return if (entity!=null && entity?.exec_progress!=null) entity?.exec_progress.toString() else "---"
    }
    @Bindable
    fun getDonetaskCnt() :String {
        return if (entity!=null && entity?.done_tasks_cnt!=null) entity?.done_tasks_cnt.toString() else "---"
    }
    @Bindable
    fun getDoneTaskLevel() :String {
        return if (entity!=null && entity?.done_tasks_hard_index!=null) entity?.done_tasks_hard_index.toString() else "---"
    }
}