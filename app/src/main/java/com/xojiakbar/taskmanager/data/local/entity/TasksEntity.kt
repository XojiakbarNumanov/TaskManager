package com.xojiakbar.taskmanager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tasks")
class TasksEntity() {
    @PrimaryKey
    var id: Int? = null
    var task_statuses_id: Int? = null
    var task_code: String? = null
    var projects_name: String? = null
    var task_priorities_name: String? = null
    var name: String? = null
    var created_date: Long? = null
    var curr_executor_name: String? = null
    var curr_executor_id : Int? = null
    var process_time: Long? = null
    var task_priorities_id: Int? = null
    var status_description: String? = null
    var new_version_code: Int? = null
    var task_statuses_name: String? = null
    var planned_start_date: String? = null
    var expired_date: String? = null
    var task_types_name: String? = null
    var task_types_id: Int? = null
    var created_users_name: String? = null
    var hard_index: Int? = null
    var time_leave: Double? = null
    var parent_name: String? =null
    var parent_id: Int? = null
    var parent_task_name: String? = null
    var projects_id: Int? = null
    var project_groups_id: Int? = null






}



