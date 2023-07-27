package com.xojiakbar.taskmanager.Utils

import android.content.Context
import android.content.SharedPreferences

object Preferences{
        private val PREFERENCES_NAME :String = "shared_preferences"
        private var sharedPreferences: SharedPreferences? = null
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
    fun getAppLanguage(): String {
        if (sharedPreferences != null )
            return sharedPreferences!!.getString("app_language", "uz").toString()
        else return "uz"
    }
    fun setAppLanguage(language: String) {
        sharedPreferences!!.edit().putString("app_language",language).apply()
    }
    fun getUserId(): Int {
        if (sharedPreferences != null )
            return sharedPreferences!!.getInt("user_id", 0)
        else
            return -1
    }
    fun setUserId(id: Int) {
        sharedPreferences!!.edit().putInt("user_id",id).apply()
    }
    fun  getUserName():String{
        if (sharedPreferences!=null)
        return sharedPreferences!!.getString("userName","").toString()
        else return ""
    }
    fun setUserName(username: String) {
        sharedPreferences!!.edit().putString("userName",username).apply()
    }
    fun getLocalPassword():String{
        if (sharedPreferences!=null)
            return sharedPreferences!!.getString("localPassword","").toString()
        else return ""
    }
    fun setLocalPassword(password: String) {
        sharedPreferences!!.edit().putString("localPassword",password).apply()
    }
    fun getUserPassword():String{
        if (sharedPreferences!=null)
            return sharedPreferences!!.getString("UserPassword","").toString()
        else return ""
    }
    fun setUserPassword(password: String) {
        sharedPreferences!!.edit().putString("UserPassword",password).apply()
    }
}