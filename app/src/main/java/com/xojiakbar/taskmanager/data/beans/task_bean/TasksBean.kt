package com.xojiakbar.taskmanager.data.beans.task_bean

data class TasksBean(
    val newest_task_date: Long,
    val oldest_task_date: Long,
    val priority_tasks: List<PriorityTask>,
    val project_tasks: List<ProjectTask>,
    val status_tasks: List<StatusTaskX>,
    val tasks: Tasks,
    val type_tasks: List<TypeTask>
)