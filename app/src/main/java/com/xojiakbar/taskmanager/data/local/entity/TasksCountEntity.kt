package com.xojiakbar.taskmanager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tasksCount")
class TasksCountEntity() {
    @PrimaryKey(autoGenerate = true)
    var id: Int? =null
    var monthly_accepted_cnt: Int? = null
    var monthly_all_cnt: Int? = null
    var monthly_done_cnt: Int? = null
    var monthly_failed_cnt: Int? = null
    var monthly_new_cnt: Int? = null
    var monthly_pause_cnt: Int? = null
    var monthly_process_cnt: Int? = null
    var monthly_ranked_cnt: Int? = null
    var monthly_returned_cnt: Int? = null
    var monthly_review_cnt: Int? = null
    var monthly_setted_cnt: Int? = null
}