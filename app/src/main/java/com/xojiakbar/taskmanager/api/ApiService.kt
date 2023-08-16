package com.xojiakbar.taskmanager.api

import com.xojiakbar.taskmanager.data.beans.ChartBean.LineChartBean
import com.xojiakbar.taskmanager.data.beans.ChartBean.ProjectGroupBean
import com.xojiakbar.taskmanager.data.beans.report_tasks_bean.ReportTasksBean
import com.xojiakbar.taskmanager.data.beans.task_bean.Task
import com.xojiakbar.taskmanager.data.beans.task_bean.TasksBean
import com.xojiakbar.taskmanager.data.beans.user_bean.UserBean
import com.xojiakbar.taskmanager.data.beans.user_bean.dashboar_task_bean.StatusTask
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

    @POST("/api/admin/auth/reset-password")
    fun changeUserPassword(@Body map: Map<String, String>): Call<ResponseBody>
    @POST("/api/admin/auth/logout")
    fun logOut(): Call<ResponseBody>
    @GET("/api/fp/tasks/taskstype")
    fun getTasksCnt(@Query("curr_executor_id") userId : Int) : Call<List<StatusTask>>
    @GET("/api/fp/tasks/new")
    fun getEPTasks(@Query("curr_executor_id") userId : Int,@Query("is_new_version") isNewVersion:Int,@Query("is_now") isNow:Int,@Query("is_my_team") isMyTeam : Int) :Call<TasksBean>
    @GET("/api/fp/tasks/new")
    fun getProcessTasks(@Query("curr_executor_id") userId : Int,@Query("is_new_version") isNewVersion:Int,@Query("is_now") isNow:Int,@Query("is_my_team") isMyTeam : Int) :Call<TasksBean>
    @GET("/api/fp/tasks/new")
    fun getReviewTasks(@Query("curr_executor_id") userId : Int,@Query("is_new_version") isNewVersion:Int,@Query("is_now") isNow:Int,@Query("is_my_team") isMyTeam : Int) :Call<TasksBean>
    @GET("/api/fp/tasks/new")
    fun getNewTasks(@Query("curr_executor_id") userId : Int,@Query("is_new_version") isNewVersion:Int,@Query("is_now") isNow:Int) :Call<TasksBean>
    @POST("/api/admin/auth/forgot-password")
    fun forgotUserPassword(@Body map: Map<String, String>): Call<ResponseBody>
    @GET("/api/fp/report-tasks")
    fun getReportTasks(@Query("date_from") fromDate :String,@Query("date_to") toDate : String) : Call<ReportTasksBean>
    @PUT("/api/fp/tasks/update-status")
    fun updateTaskStatus(@Body row: Task) : Call<ResponseBody>

    @GET("api/fp/dashboard-project-group/chart")
    fun getInfoForChart(@Query("date") data : String ,@Query("is_by_date") isByDate :Int) : Call<LineChartBean>
    @GET("/api/fp/dashboard-project-group")
    fun getProjectGroup(@Query("date") data : String) :Call<ProjectGroupBean>

    @Multipart
    @POST("/api/resources/upload")
    fun sendFile(
        @Part file: MultipartBody.Part,
        @Part id: MultipartBody.Part
    ): Call<Int>
}