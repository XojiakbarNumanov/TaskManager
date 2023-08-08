package com.xojiakbar.taskmanager.data.beans.task_bean

import com.xojiakbar.taskmanager.data.beans.BaseBean

class Row()
{
    var id: Int? = null
    var task_statuses_id:Int? = null
    var task_code:String? = null
    var projects_name:String? = null
    var task_priorities_name: String? = null
    var name: String? = null
    var created_date: Long? = null
    var curr_executor_name:String? = null
    var task_priorities_id: Int? = null
    var process_time: Long? = null
    var status_description: String? = null
    var task_statuses_name:String? = null
    var file_ids: ArrayList<BaseBean>? = null

    constructor(id: Int?,
                task_statuses_id:Int?,
                task_code:String?,
                projects_name:String?,
                task_priorities_name: String?,
                name: String?,
                created_date: Long?,
                curr_executor_name:String?,
                task_priorities_id: Int?,
                process_time: Long?,
                status_description: String?,
                task_statuses_name:String?,
                file_ids: ArrayList<BaseBean>?
    ) : this(){
        this.id = id
        this.task_statuses_id = task_statuses_id
        this.task_code = task_code
        this.projects_name = projects_name
        this.task_priorities_name = task_priorities_name
        this.name = name
        this.created_date = created_date
        this.curr_executor_name = curr_executor_name
        this.task_priorities_id = task_priorities_id
        this.process_time = process_time
        this.status_description = status_description
        this.task_statuses_name = task_statuses_name
        this.file_ids = file_ids
    }
}


