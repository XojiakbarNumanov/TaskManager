package com.xojiakbar.taskmanager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("project_groups")
class ProjectGroupsEntity {
    @PrimaryKey
    var id: Int? = null
    var name: String? = null
}