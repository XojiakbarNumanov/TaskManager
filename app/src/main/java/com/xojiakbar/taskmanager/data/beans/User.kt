package com.xojiakbar.taskmanager.data.beans

import java.util.Date

data class User(
     val login: String,
     val created_date: Date,
     val fio: String,
     val email: String,
     val phone: String,
     val birth_date: Date,
     val description: String,
     val img_resource_id: Int,
     val status: Int,
     val target_id: Int,
     val parent_id: Int,
     val branchs_id: Int,
     val branches_name: String,
     val user_types_id: Int,
     val user_type_name: String,
     val user_roles_name: String,
     val languages_id: Int,
     val is_manager: Int,
     val lang_code: String,
     val level: Int,
     val updated_password: Int,
     val children: List<UserBean>,
     val time_leave: Int,
     val hard_index: Int,
     val rate: Int,
     val clever: Double,
     val is_active: Int,
     val finished_tasks_cnt: Int,
     val session_id: String,
     val last_login_time: Date,
     val token: String,
     val last_login_time_str: String
)