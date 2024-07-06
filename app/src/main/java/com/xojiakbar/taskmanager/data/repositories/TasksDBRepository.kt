package com.xojiakbar.taskmanager.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.xojiakbar.taskmanager.data.beans.ChartBean.ProjectGroupRows
import com.xojiakbar.taskmanager.data.beans.ChartBean.Rows
import com.xojiakbar.taskmanager.data.beans.projects_bean.ProjectGroup
import com.xojiakbar.taskmanager.data.beans.projects_bean.Projects
import com.xojiakbar.taskmanager.data.beans.task_bean.Task
import com.xojiakbar.taskmanager.data.beans.task_bean.TaskCnt
import com.xojiakbar.taskmanager.data.beans.tasks_group.TaskGroup
import com.xojiakbar.taskmanager.data.beans.tasks_group.TaskType
import com.xojiakbar.taskmanager.data.beans.user_bean.User
import com.xojiakbar.taskmanager.data.local.dao.AllProjectsDao
import com.xojiakbar.taskmanager.data.local.dao.ExecutorsDao
import com.xojiakbar.taskmanager.data.local.dao.LChartDao
import com.xojiakbar.taskmanager.data.local.dao.ProjectGrDao
import com.xojiakbar.taskmanager.data.local.dao.ProjectsDao
import com.xojiakbar.taskmanager.data.local.dao.ReportTasksDao
import com.xojiakbar.taskmanager.data.local.dao.TaskGrDao
import com.xojiakbar.taskmanager.data.local.dao.TaskTypesDao
import com.xojiakbar.taskmanager.data.local.dao.TasksDao
import com.xojiakbar.taskmanager.data.local.dao.TaskscntDao
import com.xojiakbar.taskmanager.data.local.dao.UsersDao
import com.xojiakbar.taskmanager.data.local.database.AppDatabase
import com.xojiakbar.taskmanager.data.local.entity.ExecutorsEntity
import com.xojiakbar.taskmanager.data.local.entity.LineChartEntity
import com.xojiakbar.taskmanager.data.local.entity.DashboardProjectGroupEntity
import com.xojiakbar.taskmanager.data.local.entity.ProjectGroupsEntity
import com.xojiakbar.taskmanager.data.local.entity.ProjectsEntity
import com.xojiakbar.taskmanager.data.local.entity.ReportTasksEntity
import com.xojiakbar.taskmanager.data.local.entity.TaskGroupEntity
import com.xojiakbar.taskmanager.data.local.entity.TaskTypesEntity
import com.xojiakbar.taskmanager.data.local.entity.TasksCountEntity
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity

class TasksDBRepository(context: Context) {
    var tasksCntDao: TaskscntDao? = null
    var tasksDao: TasksDao? = null
    var reportDao: ReportTasksDao? = null
    var lineChartDao: LChartDao? = null
    var projectGrDao: ProjectsDao? = null
    var taskGrDao: TaskGrDao? = null
    var executorsDao: ExecutorsDao? = null
    var projectsDao: AllProjectsDao? = null
    var projectgroupsDao: ProjectGrDao? = null
    var taskTypesDao: TaskTypesDao? = null
    var usersDao : UsersDao? =null


    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(context)
        tasksCntDao = appDatabase.tasksCntDao()
        tasksDao = appDatabase.taskDao()
        reportDao = appDatabase.reportDao()
        lineChartDao = appDatabase.lineDoa()
        projectGrDao = appDatabase.projectGroupsDao()
        taskGrDao = appDatabase.taskGrDao()
        executorsDao = appDatabase.executorsDao()
        projectsDao = appDatabase.projectsDao()
        projectgroupsDao = appDatabase.projectGrDao()
        taskTypesDao = appDatabase.taskTypesDoa()
        usersDao = appDatabase.userDao()
    }

    fun getTaskCnt(): LiveData<TasksCountEntity>? {
        return tasksCntDao?.getTasks()
    }
    fun deleteTaskById(id:Int) {
        tasksDao?.deleteTaskById(id)
    }

    fun getTasksDB(userId: Int?): LiveData<MutableList<TasksEntity>>? {
        return tasksDao?.getTasks(userId)
    }

    fun getTasksByIdDB(statusId: Int, userId: Int?): LiveData<MutableList<TasksEntity>>? {
        return tasksDao?.getTasksById(statusId, userId)
    }

    fun updateTasksDB(statusId: Int, id: Int) {
        tasksDao?.updateStatus(statusId, id)
    }

    fun getByIdDB(id: Int): LiveData<TasksEntity> {
        return tasksDao?.getById(id)!!
    }

    fun insetTasks(tasks: List<Task> ,isNew:Boolean) {
        if (isNew)
            tasksDao?.deleteNewTasks()
        else
        tasksDao?.deleteTasks()
        for (task in tasks) {
            if (task.task_statuses_id != null && task.task_statuses_id!! >= 8){
                continue
            }
            val tasksEntity = TasksEntity()
            tasksEntity.id = task.id
            tasksEntity.task_statuses_id = task.task_statuses_id
            tasksEntity.task_code = task.task_code
            tasksEntity.projects_name = task.projects_name
            tasksEntity.task_priorities_name = task.task_priorities_name
            tasksEntity.name = task.name
            tasksEntity.created_date = task.created_date
            tasksEntity.curr_executor_name = task.curr_executor_name
            tasksEntity.process_time = task.process_time
            tasksEntity.task_priorities_id = task.task_priorities_id
            tasksEntity.status_description = task.status_description
            tasksEntity.task_statuses_name = task.task_statuses_name
            tasksEntity.planned_start_date = task.planned_start_date
            tasksEntity.expired_date = task.expired_date
            tasksEntity.task_types_name = task.task_types_name
            tasksEntity.task_types_id = task.task_types_id
            tasksEntity.created_users_name = task.created_users_name
            tasksEntity.hard_index = task.hard_index
            tasksEntity.time_leave = task.time_leave
            tasksEntity.curr_executor_id = task.curr_executor_id
            tasksEntity.parent_id = task.parent_id
            tasksEntity.parent_name = task.parent_name
            tasksEntity.parent_task_name = task.parent_task_name
            tasksEntity.projects_id = task.projects_id
            tasksEntity.project_groups_id = task.project_groups_id
            tasksDao?.insert(tasksEntity)
        }
    }

    fun insertReportTasks(reports: List<com.xojiakbar.taskmanager.data.beans.report_tasks_bean.Row>) {
        reportDao?.deleteAll()
        for (report in reports) {

            val reportTasks = ReportTasksEntity(
                report.id,
                report.accepted_tasks_cnt,
                report.accepted_tasks_hard_index,
                report.done_tasks_cnt,
                report.done_tasks_hard_index,
                report.done_tasks_time_leave,
                report.duration,
                report.email,
                report.exec_progress,
                report.hard_index,
                report.img_resource_id,
                report.in_progress_tasks_cnt,
                report.in_progress_tasks_hard_index,
                report.in_progress_tasks_interval,
                report.name,
                report.not_accepted_tasks_cnt,
                report.not_accepted_tasks_hard_index,
                report.not_accepted_tasks_interval,
                report.ordering,
                report.pause_tasks_cnt,
                report.pause_tasks_hard_index,
                report.phone,
                report.progress_interval,
                report.rating,
                report.returned_tasks_cnt,
                report.returned_tasks_hard_index,
                report.rownum
            )
            reportDao?.insert(reportTasks)
        }
    }

    fun getReportTasksFromDB(): LiveData<MutableList<ReportTasksEntity>>? {
        return reportDao?.getReport()
    }

    fun getRTById(userId: Int): LiveData<ReportTasksEntity>? {
        return reportDao?.getById(userId)
    }

    fun getLineChartInfoByDay(): LiveData<MutableList<LineChartEntity>> {
        return lineChartDao?.getByDay()!!
    }

    fun getLineChartInfoByMonth(): LiveData<MutableList<LineChartEntity>> {
        return lineChartDao?.getByMonth()!!
    }

    fun insetInfoLChatr(rows: List<Rows>, isDayly: Boolean) {
        if (isDayly)
            lineChartDao?.deleteDays()
        else
            lineChartDao?.deleteMonths()
        for (row in rows) {
            val chartInfo = LineChartEntity()
            chartInfo.day = row.day
            chartInfo.month = row.month
            chartInfo.tasks_cnt = row.tasks_cnt
            lineChartDao?.insert(chartInfo)
        }

    }
    //for BarChart

    fun getPGDB(): LiveData<MutableList<DashboardProjectGroupEntity>> {
        return projectGrDao?.getProjects()!!
    }

    fun insetPGDB(rows: List<ProjectGroupRows>) {
        projectGrDao?.deleteAll()
        for (row in rows) {
            val projectGr = DashboardProjectGroupEntity()
            projectGr.id = row.id
            projectGr.name = row.name
            projectGr.tasks_cnt = row.tasks_cnt
            projectGrDao?.insert(projectGr)
        }
    }

    fun insertTaskGr(list: List<TaskGroup>) {
        taskGrDao?.deleteAll()
        for (i in list) {
            val tasksGr = TaskGroupEntity()
            tasksGr.id = i.id
            tasksGr.name = i.name
            tasksGr.projects_id = i.projects_id
            tasksGr.projects_name = i.projects_name
            taskGrDao?.insertTaskGr(tasksGr)

        }
    }

    fun insertExecutors(users: List<User>): Long {
        val executor = ExecutorsEntity()
        for (user in users) {
            executor.id = user.id
            executor.login = user.login
            executor.branchs_id = user.branchs_id
            executor.created_date = user.created_date
            executor.description = user.description
            executor.email = user.email
            executor.fio = user.fio
            executor.branches_name = user.branches_name
            executor.phone = user.phone
            executor.birth_date = user.birth_date
            executor.img_resource_id = user.img_resource_id
            executor.user_roles_name = user.user_roles_name
            executor.user_types_id = user.user_types_id
            executor.target_id = user.target_id
            executor.token = user.token
            executor.status = user.status
            executor.user_type_name = user.user_type_name
            executorsDao?.insert(executor)!!
        }
        return 0
    }

    fun insertProjects(projects: List<Projects>): Long {
        projectsDao?.deleteAll()
        val projectdb = ProjectsEntity()
        for (project in projects) {
            projectdb.id = project.id
            projectdb.name = project.name
            projectdb.project_categories_id = project.project_categories_id
            projectdb.project_categories_name = project.project_categories_name
            projectdb.project_statuses_id = project.project_statuses_id
            projectdb.project_groups_id = project.project_groups_id
            projectdb.manager_id = project.manager_id
            projectdb.team_id = project.team_id
            projectdb.team_name = project.team_name
            projectdb.project_managers_name = project.project_managers_name
            projectdb.project_groups_name = project.project_groups_name
            projectdb.project_statuses_name = project.project_statuses_name
            projectdb.ordering = project.ordering
            projectdb.description = project.description
            projectdb.created_date = project.created_date
            projectdb.full_name = project.full_name
            projectdb.created_users_name = project.created_users_name
            projectdb.manager_resources_id = project.manager_resources_id
            projectdb.created_user_resources_id = project.created_user_resources_id
            projectsDao?.insert(projectdb)
        }
        return 0
    }

    fun insertProjectGroups(projects: List<ProjectGroup>): Long {
        projectgroupsDao?.deleteAll()
        val projectdb = ProjectGroupsEntity()
        for (project in projects) {
            projectdb.id = project.id
            projectdb.name = project.name
            projectgroupsDao?.insert(projectdb)
        }
        return 0
    }

    fun insertTaskTypes(taskTypes: List<TaskType>): Long {
        taskTypesDao?.deleteAll()
        val tasks = TaskTypesEntity()
        for (taskType in taskTypes) {
            tasks.id = taskType.id
            tasks.name = taskType.name
            taskTypesDao?.insert(tasks)
        }
        return 0
    }
    fun insertTaskCnt(taskCnt: TaskCnt): Long {
        tasksCntDao?.deleteAll()
        val task = TasksCountEntity()
        task.monthly_accepted_cnt = taskCnt.monthly_accepted_cnt
        task.monthly_all_cnt = taskCnt.monthly_all_cnt
        task.monthly_done_cnt = taskCnt.monthly_done_cnt
        task.monthly_failed_cnt = taskCnt.monthly_failed_cnt
        task.monthly_new_cnt = taskCnt.monthly_new_cnt
        task.monthly_pause_cnt = taskCnt.monthly_pause_cnt
        task.monthly_process_cnt = taskCnt.monthly_process_cnt
        task.monthly_ranked_cnt = taskCnt.monthly_ranked_cnt
        task.monthly_returned_cnt = taskCnt.monthly_returned_cnt
        task.monthly_review_cnt = taskCnt.monthly_review_cnt
        task.monthly_setted_cnt = taskCnt.monthly_setted_cnt
        return tasksCntDao?.insert(task)!!
    }


    fun getProjectGroups(): LiveData<MutableList<ProjectGroupsEntity>> {
        return projectgroupsDao?.getProjectGr()!!
    }
    fun getExecutors(): LiveData<MutableList<ExecutorsEntity>> {
        return executorsDao?.getExecutors()!!
    }

    fun getProject(projectGroupID: Int): LiveData<MutableList<ProjectsEntity>> {
        return projectsDao?.getProjects(projectGroupID)!!
    }

    fun getTaskGrName(projectID: Int): LiveData<MutableList<TaskGroupEntity>> {
        return taskGrDao?.getTaskGr(projectID)!!
    }

    fun getTaskTypes(): LiveData<MutableList<TaskTypesEntity>> {
        return taskTypesDao?.get()!!
    }
    fun getAllTasks(): LiveData<MutableList<TasksEntity>> {
        return tasksDao?.getAllTasks()!!
    }
    fun getNewTasks(): LiveData<MutableList<TasksEntity>> {
        return tasksDao?.getNewTasks()!!
    }
    fun getprocessTasks(): LiveData<MutableList<TasksEntity>> {
        return tasksDao?.getProcessTasks()!!
    }
    fun getreviewTasks(): LiveData<MutableList<TasksEntity>> {
        return tasksDao?.getreviewTasks()!!
    }


    fun clearAllDb(){
        tasksCntDao?.deleteAll()
        tasksDao?.deleteAll()
        reportDao?.deleteAll()
        lineChartDao?.deleteAll()
        projectGrDao?.deleteAll()
        taskGrDao?.deleteAll()
        executorsDao?.deleteAll()
        projectsDao?.deleteAll()
        projectgroupsDao?.deleteAll()
        taskTypesDao?.deleteAll()
        usersDao?.deleteAll()

    }
}