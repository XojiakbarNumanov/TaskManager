package com.xojiakbar.taskmanager.api

import com.xojiakbar.taskmanager.data.beans.user_bean.UserBean
import com.xojiakbar.taskmanager.data.beans.user_bean.dashboar_task_bean.DashboardTaskcntBean
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
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
    @GET("/api/fp/dashboard/headers")
    fun getTasksCnt(@Query("curr_executor_id") userId : Int) : Call<DashboardTaskcntBean>
    @POST("/api/admin/auth/forgot-password")
    fun forgotUserPassword(@Body map: Map<String, String>): Call<ResponseBody>
}