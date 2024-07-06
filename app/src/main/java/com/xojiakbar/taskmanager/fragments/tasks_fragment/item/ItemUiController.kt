package com.xojiakbar.taskmanager.fragments.tasks_fragment.item

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Html
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.xojiakbar.taskmanager.BR
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.fragments.tasks_fragment.TasksRouter
import java.text.SimpleDateFormat
import java.util.Date


class ItemUiController(val context: Context) : BaseObservable() {
    var router: TasksRouter? = null
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
    fun getProgress(): Int {
        if (entity?.time_leave != null && entity?.time_leave != 0.0) {
            val time = entity?.time_leave!! * 3600
            return (entity?.process_time!! * 100 / time).toInt()
        } else return 0
    }

    @Bindable
    fun getProgressPersent(): String {
        return getProgress().toString() + "%"
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Bindable
    fun getBackProgress(): Drawable {
        return if (getProgress() >= 100) context.resources.getDrawable(R.drawable.back_progress_bar_full) else context.resources.getDrawable(
            R.drawable.back_progress_bar
        )
    }

    @Bindable
    fun getLevel(): String {
        return if (entity != null && entity?.task_priorities_name != null) entity?.task_priorities_name.toString() else "---"
    }

    @Bindable
    fun getName(): CharSequence {

        return if (entity != null && entity?.name != null)
        {
            if (entity?.parent_name != null) {
                Html.fromHtml("<FONT COLOR=\"#1815C0\">${entity?.parent_name}/</FONT><spam>${entity?.name}</spam>")
            }
            else
                Html.fromHtml("${entity?.name}").toString()

        }
        else "---"
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
    fun getIsPlay(): Boolean {
        return entity?.task_statuses_id == 4
    }

    fun getIsPause(): Boolean {
        return entity?.task_statuses_id == 5 || entity?.task_statuses_id == 3
    }

    fun getIsNotDownload(): Boolean {
        return entity?.task_statuses_id == 2 || entity?.task_statuses_id == 7
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

    @Bindable
    fun getToCreatedDate(): String {
        if (entity != null && entity?.created_date != null) {
            val date = System.currentTimeMillis()
            var time = (date - entity?.created_date!!) / 60000
            val min = time % 60
            time /= 60
            val hour = time % 24
            val day = time / 24
            return (if (day > 0) "${day} kun " else "") + (if (hour >= 10) "$hour:" else "0${hour}:") + (if (min >= 10) "$min" else "0${min}")

        } else return "---"
    }

    @SuppressLint("SimpleDateFormat")
    @Bindable
    fun getProcessTime(): String {
        if (entity != null && entity?.process_time != null) {
            var time = entity?.process_time!! / 60 //415
            val min = time % 60
            time /= 60
            val hour = time % 24
            val day = time / 24
            return (if (day > 0) "${day} kun " else "") + (if (hour >= 10) "$hour:" else "0$hour:") + (if (min >= 10) "$min" else "0${min}")
        } else return "---"
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
        if (entity?.expired_date != null)
            return entity?.expired_date.toString()
        else return "---"
    }

    @Bindable
    fun getCreatorName(): String {
        if (entity?.created_users_name != null)
            return entity?.created_users_name.toString()
        else return "---"
    }

    @Bindable
    fun getTaskType(): String {
        if (entity?.task_types_name != null)
            return entity?.task_types_name.toString()
        else return "---"
    }

    @Bindable
    fun getIsDropDown(): Boolean {
        return dropDown
    }

    fun clickDropDown() {
        dropDown = !dropDown
        notifyPropertyChanged(BR._all)
    }

    fun btnClick() {
        router?.changeStatus(entity!!)
    }

    fun btnUpload() {
        router?.showDialog(entity!!)
    }

}