package com.xojiakbar.taskmanager.api

import com.xojiakbar.taskmanager.data.beans.ChartBean.LineChartBean
import com.xojiakbar.taskmanager.data.beans.ChartBean.DashboardProjectGroupBean
import com.xojiakbar.taskmanager.data.beans.projects_bean.ProjectGroupsBean
import com.xojiakbar.taskmanager.data.beans.projects_bean.ProjectsBean
import com.xojiakbar.taskmanager.data.beans.report_tasks_bean.ReportTasksBean
import com.xojiakbar.taskmanager.data.beans.task_bean.Task
import com.xojiakbar.taskmanager.data.beans.task_bean.Tasks
import com.xojiakbar.taskmanager.data.beans.task_bean.TasksBean
import com.xojiakbar.taskmanager.data.beans.tasks_group.TaskTypesBean
import com.xojiakbar.taskmanager.data.beans.tasks_group.TasksGrBean
import com.xojiakbar.taskmanager.data.beans.user_bean.User
import com.xojiakbar.taskmanager.data.beans.user_bean.UserBean
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @POST("/api/admin/auth/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String?,
        @Field("password") password: String?,
        @Field("remember") remember: Boolean?,
    ): Call<UserBean>



    @GET("/api/fp/tasks/executors")
    fun getExecutors(@Query("isTeamMembers") isTeamMembers: Int): Call<List<User>>

    @GET("/api/fp/task-types")
    fun getTaskTayps(): Call<TaskTypesBean>

    @GET("/api/fp/project-groups")
    fun getProjectGroups(@Query("isTeamMembers") isTeamMembers: Int): Call<ProjectGroupsBean>

    @GET("/api/fp/projects")
    fun getProjects(@Query("isTeamMembers") isTeamMembers: Int): Call<ProjectsBean>

    @POST("/api/admin/auth/reset-password")
    fun changeUserPassword(@Body map: Map<String, String>): Call<ResponseBody>

    @POST("/api/admin/auth/logout")
    fun logOut(): Call<ResponseBody>


    @GET("/api/fp/tasks/new")
    fun getNewTasks(
        @Query("curr_executor_id") userId: Int?,
        @Query("is_now") isNow: Int,
        @Query("is_my_team") isMyTeam: Int
    ): Call<TasksBean>


    @POST("/api/admin/auth/forgot-password")
    fun forgotUserPassword(@Body map: Map<String, String>): Call<ResponseBody>

    @GET("/api/fp/report-tasks")
    fun getReportTasks(
        @Query("date_from") fromDate: String,
        @Query("date_to") toDate: String
    ): Call<ReportTasksBean>

    @PUT("/api/fp/tasks/update-status")
    fun updateTaskStatus(@Body row: Task): Call<ResponseBody>

    @GET("api/fp/dashboard-project-group/chart")
    fun getInfoForChart(
        @Query("date") data: String,
        @Query("is_by_date") isByDate: Int
    ): Call<LineChartBean>

    @GET("/api/fp/dashboard-project-group")
    fun getDashProjectGroup(@Query("date") data: String): Call<DashboardProjectGroupBean>

    @GET("/api/fp/task-groups")
    fun getTasksGroup(): Call<TasksGrBean>

    @Multipart
    @POST("/api/resources/upload")
    fun sendFile(
        @Part file: MultipartBody.Part,
        @Part id: MultipartBody.Part
    ): Call<Int>

    @POST("/api/fp/tasks")
    fun createTask(@Body task: Task) : Call<ResponseBody>

}