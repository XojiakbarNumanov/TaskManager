package com.xojiakbar.taskmanager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tasks")
class TasksEntity(
    @PrimaryKey
    var id: Int?,
    var task_statuses_id: Int?,
    var task_code: String?,
    var projects_name: String?,
    var task_priorities_name: String?,
    var name: String?,
    var created_date: Long?,
    var curr_executer_name: String?,
    var process_time: Long?,
    var task_priorities_id : Int?,
    var status_description:String?,
    var new_version_code: Int?,
    var task_statuses_name :String?,

    )