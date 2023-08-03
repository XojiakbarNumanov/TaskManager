package com.xojiakbar.taskmanager.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("report_tasks")
class ReportTasksEntity (
    @PrimaryKey
    var id : Int?,
    var accepted_tasks_cnt: Int?,
    var accepted_tasks_hard_index: Int?,
    var done_tasks_cnt: Int?,
    var done_tasks_hard_index: Int?,
    var done_tasks_time_leave: Int?,
    var duration: Int?,
    var email: String?,
    var exec_progress: Int?,
    var hard_index: Int?,
    var img_resource_id: Int?,
    var in_progress_tasks_cnt: Int?,
    var in_progress_tasks_hard_index: Int?,
    var in_progress_tasks_interval: Int?,
    var name: String?,
    var not_accepted_tasks_cnt: Int?,
    var not_accepted_tasks_hard_index: Int?,
    var not_accepted_tasks_interval: Int?,
    var ordering: Int?,
    var pause_tasks_cnt: Int?,
    var pause_tasks_hard_index: Int?,
    var phone: String?,
    var progress_interval: Int?,
    var rating: Int?,
    var returned_tasks_cnt: Int?,
    var returned_tasks_hard_index: Int?,
    var rownum: Int?
        )
