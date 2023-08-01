package com.xojiakbar.taskmanager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tasks_count")
class TasksCount (
    @PrimaryKey
    private var id :Int,
    private var prosses_tasks_cnt : Int
)