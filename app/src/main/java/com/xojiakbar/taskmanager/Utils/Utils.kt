package com.xojiakbar.taskmanager.Utils

import android.content.Context
import android.content.res.Configuration
import com.xojiakbar.taskmanager.api.result.ErrorResult
import okhttp3.ResponseBody
import org.json.JSONObject
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Locale

object Utils {
    fun getLocalStringByResId(context: Context, resId: Int): String? {
        val appLang = Preferences.getAppLanguage()
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(Locale(appLang))
        return context.createConfigurationContext(configuration).resources.getString(resId)
    }
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
    fun getStringDigest(password: String): String? {
        var s: String? = null
        try {
            val digest = MessageDigest.getInstance("SHA1")
            digest.update(password.toByteArray())
            s = toHexString(digest.digest())
                ?.lowercase(Locale.getDefault())
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return s
    }
open fun toHexString(block: ByteArray): String? {
    val buf = StringBuffer()
    for (b in block) {
        byte2hex(b, buf)
    }
    return buf.toString()
}
    private fun byte2hex(b: Byte, buf: StringBuffer) {
        val hexChars = charArrayOf(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'
        )
        val high = b.toInt() and 0xf0 shr 4
        val low = b.toInt() and 0xf
        buf.append(hexChars[high])
        buf.append(hexChars[low])
    }

}