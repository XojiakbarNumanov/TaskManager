package com.xojiakbar.taskmanager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity("executors")
class ExecutorsEntity {
    @PrimaryKey
    var id: Int? = null
    var login: String? = null
    var branchs_id: Int? = null
    var created_date: Long? = null
    var description: String? = null
    var email: String? = null
    var fio: String? = null
    var branches_name: String? = null
    var phone: String? = null
    var birth_date: Long? = null
    var img_resource_id: Int? = null
    var user_roles_name: String? = null
    var user_types_id: Int? = null
    var target_id: Int? = null
    var token: String? = null
    var status: Int? = null
    var user_type_name: String? = null

}