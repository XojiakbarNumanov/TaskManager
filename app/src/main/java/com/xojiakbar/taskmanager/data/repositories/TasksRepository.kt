package com.xojiakbar.taskmanager.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.ApiService
import com.xojiakbar.taskmanager.data.beans.task_bean.Row
import com.xojiakbar.taskmanager.data.beans.task_bean.TasksBean
import com.xojiakbar.taskmanager.data.beans.user_bean.dashboar_task_bean.StatusTask
import com.xojiakbar.taskmanager.data.local.dao.TasksDao
import com.xojiakbar.taskmanager.data.local.dao.TaskscntDao
import com.xojiakbar.taskmanager.data.local.database.AppDatabase
import com.xojiakbar.taskmanager.data.local.entity.TasksCountEntity
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import uz.furorprogress.domain.base.BaseRepository

class TasksRepository(context: Context) : BaseRepository<ApiService>(context) {
    override val apiService: Class<ApiService>?
        get() = apiService
    var tasksCntDao : TaskscntDao? =null
    var tasksDao : TasksDao? =null

    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(context)
        tasksCntDao = appDatabase.tasksCntDao()
        tasksDao = appDatabase.taskDao()
    }
    fun getTasksCnt(userID : Int ,callback: ApiCallback<List<StatusTask>>) {
        request(getApi(ApiService::class.java).getTasksCnt(userID), callback)
    }
    fun insertTasksCount(tasks: List<StatusTask>): Long {
        tasksCntDao?.deleteAll()
        var newtasks= 0
        var acceptedTasks= 0
        var doneTasks= 0
        var returnedTasks=0
        var prossesTasks= 0
        for (task  in tasks)
        {
            if (task.id == 1){newtasks += task.cnt}
            if (task.id == 3){acceptedTasks += task.cnt}
            if (task.id == 4 || task.id == 5){prossesTasks+=task.cnt}
            if (task.id == 8){ doneTasks += task.cnt}
            if (task.id == 7){returnedTasks += task.cnt}
        }

        val tasksCnt = TasksCountEntity(
            newtasks,
            prossesTasks,
            doneTasks,
            returnedTasks,
            acceptedTasks
        )
        return tasksCntDao?.insert(tasksCnt)!!
    }
    fun getTaskCnt() : LiveData<TasksCountEntity>? {
        return tasksCntDao?.getTasks()
    }
    fun getTasksDB():LiveData<MutableList<TasksEntity>>?{
        return tasksDao?.getTasks()
    }
    fun getNewTasks(userID : Int ,callback: ApiCallback<TasksBean>) {
        request(getApi(ApiService::class.java).getNewTasks(userID,1,2), callback)
    }
    fun getEPTasks(userID : Int ,callback: ApiCallback<TasksBean>) {
        request(getApi(ApiService::class.java).getEPTasks(userID,2,2,1), callback)
    }
    fun getProcessTasks(userID : Int ,callback: ApiCallback<TasksBean>) {
        request(getApi(ApiService::class.java).getProcessTasks(userID,3,2,1), callback)
    }
    fun getReviewTasks(userID : Int ,callback: ApiCallback<TasksBean>) {
        request(getApi(ApiService::class.java).getEPTasks(userID,4,2,1), callback)
    }
    fun insetTasks(tasks : List<Row>,newVersioncode:Int){
        tasksDao?.deleteAll(newVersioncode)
        for (task in tasks){
            val tasksEntity  = TasksEntity(
                task.id,
                task.task_statuses_id,
                task.task_code,
                task.projects_name,
                task.task_priorities_name,
                task.name,
                task.created_date,
                task.curr_executor_name,
                task.process_time,
                task.task_priorities_id,
                newVersioncode
            )
            tasksDao?.insert(tasksEntity)
        }

    }
}