package com.xojiakbar.taskmanager.fragments.home_fragment.item

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.xojiakbar.taskmanager.BR
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity

import com.xojiakbar.taskmanager.fragments.home_fragment.HomeRouter
import java.text.SimpleDateFormat
import java.util.Date

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
    @Bindable
    fun getProjectName() :String{
        return if (entity!=null && entity?.projects_name!=null) entity?.projects_name.toString() else "---"
    }
    @Bindable
    fun getLevel() :String{
        return if (entity!=null && entity?.task_priorities_name!=null) entity?.task_priorities_name.toString() else "---"
    }
    @Bindable
    fun getName() :String{
        return if (entity!=null && entity?.name!=null) entity?.name.toString() else "---"
    }
    @Bindable
    fun getUserName() :String{
        return if (entity!=null && entity?.curr_executer_name!=null) entity?.curr_executer_name.toString() else "---"
    }
    @Bindable
    fun getIsPlay(): Boolean{
        return entity?.task_statuses_id == 4
    }
    fun getIsPause(): Boolean{
        return entity?.task_statuses_id == 5 || entity?.task_statuses_id == 3
    }
    fun getIsNotDownload(): Boolean{
        return entity?.task_statuses_id == 2
    }
    @SuppressLint("SuspiciousIndentation")
    @Bindable
    fun getCreatedDate() :String{
        if (entity!=null && entity?.created_date!=null){
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = Date(entity?.created_date!!)
        val formattedDate = dateFormat.format(date)
            return formattedDate
        }
        else return "---"
    }
    @Bindable
    fun getProcessTime() :String{
        if (entity!=null && entity?.process_time!=null){
            val dateFormat = SimpleDateFormat("HH:mm:ss")
            val date = Date(entity?.process_time!!)
            val formattedDate = dateFormat.format(date)
            return formattedDate
        }
        else return "---"
    }
    @Bindable
    fun getBackColor() :Int {
        return when{
            entity?.new_version_code == 1 ->
              context.resources.getColor(R.color.dark_blue)

            entity?.new_version_code == 2 ->
                context.resources.getColor(R.color.dark_violet)

            entity?.new_version_code == 3 ->
                context.resources.getColor(R.color.sunglow)

            else  ->
                context.resources.getColor(R.color.green)
        }
    }
    @Bindable
    fun getLayoutColor() :Int {
        return when{
            entity?.new_version_code == 1 ->
                context.resources.getColor(R.color._light_blue)

            entity?.new_version_code == 2 ->
                context.resources.getColor(R.color._light_violet)

            entity?.new_version_code == 3 ->
                context.resources.getColor(R.color.light_sunglow)

            else  ->
                context.resources.getColor(R.color._light_green)
        }
    }
    @Bindable
    fun getLevelColor() :Int {
        return when{
            entity?.task_priorities_id == 1 ->
                context.resources.getColor(R.color.red)

            entity?.task_priorities_id == 2 ->
                context.resources.getColor(R.color.orange)

            else  ->
                context.resources.getColor(R.color.dark_blue)
        }
    }

    fun btnClick(){
        router?.changeStatus(entity?.id)
    }
    fun btnClickDownload(){

    }


}