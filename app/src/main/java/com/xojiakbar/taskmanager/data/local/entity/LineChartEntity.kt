package com.xojiakbar.taskmanager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("line_chart")
class LineChartEntity {
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
    var tasks_cnt: Int? = null
    var day: Long? = null
    var month: String? = null
}