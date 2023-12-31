package com.xojiakbar.taskmanager.api

import android.content.Context
import com.xojiakbar.taskmanager.BuildConfig
import me.sianaki.flowretrofitadapter.FlowCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    var retrofit: Retrofit? = null

    fun initClient(context:Context): Retrofit? {
        if (retrofit == null) {
            val client = OkhttpProvider.getOkhttpClient(context)
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
    //val apiService = initClient(context )?.create(ApiService::class.java)
}