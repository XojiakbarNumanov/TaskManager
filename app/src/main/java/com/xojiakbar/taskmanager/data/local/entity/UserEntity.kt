package com.xojiakbar.taskmanager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("users")
class UserEntity {
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

    constructor(
        id: Int?,
        login: String?,
        branchs_id: Int?,
        created_date: Long?,
        description: String?,
        email: String?,
        fio: String?,
        branches_name: String?,
        phone: String?,
        birth_date: Long?,
        img_resource_id: Int?,
        user_roles_name: String?,
        user_types_id: Int?,
        target_id: Int?,
        token: String?,
        status: Int?,
        user_type_name: String?
    ) {
        this.id = id
        this.login = login
        this.branchs_id = branchs_id
        this.created_date = created_date
        this.description = description
        this.email = email
        this.fio = fio
        this.branches_name = branches_name
        this.phone = phone
        this.birth_date = birth_date
        this.img_resource_id = img_resource_id
        this.user_roles_name = user_roles_name
        this.user_types_id = user_types_id
        this.target_id = target_id
        this.token = token
        this.status = status
        this.user_type_name = user_type_name
    }
}
