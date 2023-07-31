package com.xojiakbar.taskmanager.api

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.xojiakbar.taskmanager.BuildConfig
import com.xojiakbar.taskmanager.Utils.Preferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.String
import java.util.concurrent.TimeUnit

object OkhttpProvider {
    fun getOkhttpClient(context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        var level = logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(createChucker(context))
            .addInterceptor(logging)
            .addInterceptor { chain: Interceptor.Chain ->
                val request = chain.request()
                    .newBuilder()
                if (Preferences.getUserName().equals("")) {
                    request.addHeader("Content-Type", "application/x-www-form-urlencoded")
                } else {
                    request.addHeader("Content-Type", "application/json")
                }
                request.addHeader("os", "android")
                    .addHeader("version",BuildConfig.VERSION_NAME)
                    .addHeader("language", Preferences.getAppLanguage())
                    .addHeader("user_id", String.valueOf(Preferences.getUserId()))
                    .addHeader(
                        "Authorization",
                        Preferences.getUserName() + ":" + Preferences.getUserPassword()
                    )
                chain.proceed(request.build())
            }.build()
    }

    private fun createChucker(context: Context): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context,
            true,
            RetentionManager.Period.ONE_HOUR
        )

        // Create the Interceptor
        return ChuckerInterceptor.Builder(context) // The previously created Collector
            .collector(chuckerCollector) // The max body content length in bytes, after this responses will be truncated.
            .maxContentLength(250000L) // List of headers to replace with ** in the Chucker UI
            .redactHeaders(
                "Auth-Token",
                "Bearer"
            ) // Read the whole response body even when the client does not consume the response completely.
            // This is useful in case of parsing errors or when the response body
            // is closed before being read like in Retrofit with Void and Unit types.
            .alwaysReadResponseBody(true)
            .build()
    }
}
