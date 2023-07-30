package com.xojiakbar.taskmanager.api

import com.xojiakbar.taskmanager.data.beans.UserBean
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST("/api/admin/auth/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String?,
        @Field("password") password: String?,
        @Field("remember") remember: Boolean?,
    ): Call<UserBean>

    @POST("/api/admin/auth/forgot-password")
    fun forgotUserPassword(@Body map: Map<String?, String?>?): Call<ResponseBody?>?
    @POST("/api/admin/auth/set-new-password")
    fun changeUserPassword(@Body map: Map<String?, String?>?): Call<ResponseBody?>?

}