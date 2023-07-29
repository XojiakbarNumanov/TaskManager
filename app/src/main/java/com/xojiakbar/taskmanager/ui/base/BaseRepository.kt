package com.xojiakbar.taskmanager.ui.base

import android.app.Application
import android.content.Context
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Utils
import com.xojiakbar.taskmanager.api.ApiCallback
import com.xojiakbar.taskmanager.api.ApiClient
import com.xojiakbar.taskmanager.api.ApiService
import com.xojiakbar.taskmanager.api.result.ErrorResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


abstract class BaseRepository(application: Application) {
    private var retrofitClient: Retrofit? = null
    private val context: Context
    private var msg: String? = null

    init {
        context = application
    }

    val api: ApiService
        get() {
            if (retrofitClient == null) retrofitClient = ApiClient.initClient()
            return retrofitClient!!.create(ApiService::class.java)
        }

    fun <T> request(call: Call<T>, loaderCallback: ApiCallback<T>) {
        msg = ""
        call.enqueue(object : Callback<T?> {
            override fun onResponse(call: Call<T?>, response: Response<T?>) {
                if (response.isSuccessful && response.body() != null) loaderCallback.onSuccess(
                    response.body()!!
                ) else {
                    val errorResult: ErrorResult = Utils.errorParser(response.errorBody()!!)!!
                    if (errorResult.code !== -9999) {
                        loaderCallback.onErrorMsg(errorResult)
                    } else {
                        loaderCallback.onErrorMsg(ErrorResult(context.resources.getString(R.string.failure_to_connect)))
                    }
                }
            }

            override fun onFailure(call: Call<T?>, t: Throwable) {
                loaderCallback.onErrorMsg(ErrorResult(context.resources.getString(R.string.failure_to_connect)))
            }
        })
    }
}
