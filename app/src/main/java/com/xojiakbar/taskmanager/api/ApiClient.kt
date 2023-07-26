package com.xojiakbar.taskmanager.api

class ApiClient {
    val  baseUrl = "http://143.198.129.165:8000/api/"

    fun getApiService() : Apiservice
    {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Apiservice::class.java)
    }
}