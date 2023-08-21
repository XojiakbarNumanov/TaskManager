package com.xojiakbar.taskmanager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("dashboard_project_groups")
class DashboardProjectGroupEntity {
    @PrimaryKey
    var id: Int? = null
    var tasks_cnt: Int? = null
    var name: String? = null
}