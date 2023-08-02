package com.xojiakbar.taskmanager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tasksCount")
class TasksCountEntity(
    var new_tasks: Int,
    var prosses_tasks_cnt: Int,
    var done_tasks: Int,
    var returned_tasks: Int,
    var accepted_tasks: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? =null
}