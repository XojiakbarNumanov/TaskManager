package com.xojiakbar.taskmanager.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    val  baseUrl = "http://143.198.129.165:8000/api/"

    fun getApiService() : ApiService
    {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}