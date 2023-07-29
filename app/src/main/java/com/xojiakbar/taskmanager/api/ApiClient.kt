package com.xojiakbar.taskmanager.api

import android.content.Context
import com.google.gson.GsonBuilder
import com.xojiakbar.taskmanager.BuildConfig
import me.sianaki.flowretrofitadapter.FlowCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    var retrofit: Retrofit? = null

    fun initClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(FlowCallAdapterFactory.create())
                .build()
        }
        return retrofit
    }
    val apiService = initClient()?.create(ApiService::class.java)
}