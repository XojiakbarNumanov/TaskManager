package com.xojiakbar.taskmanager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("projects")
class ProjectGroupEntity {
    @PrimaryKey
    var id: Int? = null
    var tasks_cnt: Int? = null
    var name: String? = null
}