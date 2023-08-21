package com.xojiakbar.taskmanager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("task_group")
class TaskGroupEntity {
    @PrimaryKey
    var id :Int?= null
    var name: String? = null
    var projects_name: String? = null
    var projects_id: Int? = null
}