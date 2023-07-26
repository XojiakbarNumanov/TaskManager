package com.xojiakbar.taskmanager.Utils

import android.content.Context
import android.content.SharedPreferences

class Preferences(context :Context){
        private val PREFERENCES_NAME :String = "shared_preferences"
        private var sharedPreferences: SharedPreferences? = null
     init {
         sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME,Context.MODE_PRIVATE)
     }
    fun getAppLanguage(): String? {
        if (sharedPreferences != null )
            return sharedPreferences!!.getString("app_language", "uz")
        else return "uz"
    }
    fun setAppLanguage(language: String) {
        sharedPreferences!!.edit().putString("app_language",language).apply()
    }
    fun getUserName():String{
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
}