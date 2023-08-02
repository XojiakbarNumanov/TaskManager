package com.xojiakbar.taskmanager.data.beans.task_bean

data class Row(
    var id : Int,
    var task_statuses_id :Int,
    var task_code :String,
    var projects_name:String,
    var task_priorities_name : String,
    var name : String,
    var created_date : Long,
    var curr_executor_name :String,
    var task_priorities_id : Int,
    var process_time : Long
    )