package com.xojiakbar.taskmanager.fragments.tasks_for_admin.item

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.xojiakbar.taskmanager.BR
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.fragments.tasks_for_admin.TaskRouter
import com.xojiakbar.taskmanager.fragments.tasks_fragment.TasksRouter
import java.text.SimpleDateFormat
import java.util.Date

class ItemUiController (val context: Context) : BaseObservable() {
    var router: TaskRouter? = null
    var entity: TasksEntity? = null
    var dropDown = false


    fun setEntityInfo(entity: TasksEntity?) {
        this.entity = entity
        notifyPropertyChanged(BR._all)
    }


    @Bindable
    fun getProjectName(): String {
        return if (entity != null && entity?.projects_name != null) entity?.projects_name.toString() else "---"
    }

    @Bindable
    fun getLevel(): String {
        return if (entity != null && entity?.task_priorities_name != null) entity?.task_priorities_name.toString() else "---"
    }

    @Bindable
    fun getName(): String {
        return if (entity != null && entity?.name != null) entity?.name.toString() else "---"
    }

    @Bindable
    fun getUserName(): String {
        return if (entity != null && entity?.curr_executor_name != null) entity?.curr_executor_name.toString() else "---"
    }

    @Bindable
    fun getStatus(): String {
        return if (entity != null && entity?.task_statuses_name != null) entity?.task_statuses_name.toString() else "---"
    }

    @Bindable
    fun getIsDone(): Boolean {
        return entity?.task_statuses_id == 6
    }

    @Bindable
    fun getIsNew(): Boolean {
        return entity?.task_statuses_id == 1 || entity?.task_statuses_id == 0
    }

            @SuppressLint("SuspiciousIndentation", "SimpleDateFormat")
    @Bindable
    fun getCreatedDate(): String {
        if (entity != null && entity?.created_date != null) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = Date(entity?.created_date!!)
            val formattedDate = dateFormat.format(date)
            return formattedDate
        } else return "---"
    }

    @SuppressLint("SimpleDateFormat")
    @Bindable
    fun getProcessTime(): String {
        if (entity != null && entity?.process_time != null) {
            val dateFormat = SimpleDateFormat("HH:mm:ss")
            val date = Date(entity?.process_time!!)
            val formattedDate = dateFormat.format(date)
            return formattedDate
        } else return "---"
    }

    @Bindable
    fun getLayoutColor(): Int {
        return when {
            entity?.task_statuses_id == 6 ->
                context.resources.getColor(R.color._light_green)

            entity?.task_statuses_id == 4 ->
                context.resources.getColor(R.color._light_blue)

            entity?.task_statuses_id == 3 ->
                context.resources.getColor(R.color.light_sunglow)

            entity?.task_statuses_id == 5 ->
                context.resources.getColor(R.color._light_violet)

            else ->
                context.resources.getColor(R.color._light_red)
        }
    }

    @Bindable
    fun getLevelColor(): Int {
        return when {
            entity?.task_priorities_id == 1 ->
                context.resources.getColor(R.color.light_red)

            entity?.task_priorities_id == 2 ->
                context.resources.getColor(R.color.light_orange)

            else ->
                context.resources.getColor(R.color.color_light_primary)
        }
    }
    @Bindable
    fun getTextColor(): Int {
        return when {
            entity?.task_priorities_id == 1 ->
                context.resources.getColor(R.color.red)

            entity?.task_priorities_id == 2 ->
                context.resources.getColor(R.color.orange)

            else ->
                context.resources.getColor(R.color.color_primary)
        }
    }

    @Bindable
    fun getTimeLeave(): String {
        return if (entity?.time_leave != null) entity?.time_leave.toString()
        else "---"
    }

    @Bindable
    fun getHardIndex(): String {
        return if (entity?.hard_index != null) entity?.hard_index.toString()
        else "---"
    }

    @Bindable
    fun getGrade(): String {
        return if (entity?.time_leave != null && entity?.hard_index != null) {
            (entity?.time_leave!!.toDouble() + (entity?.time_leave!!.toDouble() * entity?.hard_index!!.toDouble() / 10)).toString()
        } else "---"
    }

    @Bindable
    fun getStartTime(): String {
        return entity?.planned_start_date.toString()
    }

    @Bindable
    fun getEndTime(): String {
        return entity?.expired_date.toString()
    }

    @Bindable
    fun getIsDropDown(): Boolean {
        return dropDown
    }

    fun clickDropDown() {
        dropDown = !dropDown
        notifyPropertyChanged(BR._all)
    }
    fun btnAttachTask(){
        router?.showDialog(entity!!)
    }



}