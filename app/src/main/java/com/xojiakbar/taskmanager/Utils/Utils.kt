package com.xojiakbar.taskmanager.Utils

import com.xojiakbar.taskmanager.api.result.ErrorResult
import okhttp3.ResponseBody
import org.json.JSONObject

object Utils {
    fun errorParser(responseBody: ResponseBody): ErrorResult? {
        var errorResult = ErrorResult(-1, -9999, "", "")
        try {
            val jObjError = JSONObject(responseBody.string())
            errorResult.code = (jObjError.getJSONObject("ERROR").getInt("code"))
            errorResult.status = (jObjError.getJSONObject("ERROR").getInt("status"))
            if (jObjError.getJSONObject("ERROR")
                    .getString("message") != "null"
            ) errorResult.message = (jObjError.getJSONObject("ERROR").getString("message"))
            if (jObjError.getJSONObject("ERROR")
                    .getString("description") != "null"
            ) errorResult.description = (jObjError.getJSONObject("ERROR").getString("description"))
            if (jObjError.getJSONObject("ERROR").has("download_url")) {
                errorResult.download_url = (
                    jObjError.getJSONObject("ERROR").getString("download_url")
                )
            }
            if (jObjError.getJSONObject("ERROR").has("has_new_version")) {
                errorResult.isHas_new_version = (
                    jObjError.getJSONObject("ERROR").getBoolean("has_new_version")
                )
            }
            if (jObjError.getJSONObject("ERROR").has("version_name")) {
                errorResult.version_name = (
                    jObjError.getJSONObject("ERROR").getString("version_name")
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (errorResult.message == null) errorResult = ErrorResult(-1, -1, "", "")
        return errorResult
    }
}