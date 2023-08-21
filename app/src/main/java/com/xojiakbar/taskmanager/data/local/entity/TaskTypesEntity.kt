package com.xojiakbar.taskmanager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("task_types")
 class TaskTypesEntity {
    @PrimaryKey
    var id: Int? = null
    var name: String? = null
}