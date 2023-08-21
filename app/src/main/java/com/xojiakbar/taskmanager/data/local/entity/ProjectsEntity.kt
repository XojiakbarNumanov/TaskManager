package com.xojiakbar.taskmanager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("projects")
class ProjectsEntity {
    @PrimaryKey
    var id : Int? = null
    var name: String? = null
    var project_categories_id: Int? = null
    var project_categories_name: String? = null
    var project_statuses_id: Int? = null
    var project_groups_id: Int? = null
    var manager_id: Int? = null
    var team_id: Int? = null
    var team_name: String? = null
    var project_managers_name: String? = null
    var project_groups_name: String? = null
    var project_statuses_name: String? = null
    var ordering: Int? = null
    var description: String? = null
    var created_date: Long? = null
    var full_name: String? = null
    var created_users_name: String? = null
    var manager_resources_id: Int? = null
    var created_user_resources_id: Int? = null
}