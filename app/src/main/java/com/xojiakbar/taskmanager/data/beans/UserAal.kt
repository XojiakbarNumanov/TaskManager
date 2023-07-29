package com.xojiakbar.taskmanager.data.beans

data class UserAal(
     val id: Int,
     val rownum: Int,
     val app_actions_id: Int,
     val user_roles_id: Int,
     val access_state: Int,
     val description: String,
     val app_action_types_id: Int,
     val app_action_categories_id: Int,
     val name: String,
     val app_action_type_name: String,
     val app_action_category_name: String
)