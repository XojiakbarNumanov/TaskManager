package com.xojiakbar.taskmanager.Utils

import android.content.Context
import android.content.SharedPreferences

object Preferences{
        private val PREFERENCES_NAME :String = "shared_preferences"
        private var sharedPreferences: SharedPreferences? = null
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
    fun getIsFirst() :Boolean {
        return sharedPreferences!!.getBoolean("is_first", true)
    }
    fun setIsFirst(isFirst: Boolean) {
        sharedPreferences!!.edit().putBoolean("is_first",isFirst).apply()
    }
    fun getAppLanguage(): String {
            return sharedPreferences!!.getString("app_language", "uz").toString()
    }
    fun setAppLanguage(language: String) {
        sharedPreferences!!.edit().putString("app_language",language).apply()
    }
    fun getUserId(): Int {
            return sharedPreferences!!.getInt("user_id", 0)
    }
    fun setUserId(id: Int) {
        sharedPreferences!!.edit().putInt("user_id",id).apply()
    }
    fun  getUserName():String{
        return sharedPreferences!!.getString("userName","").toString()
    }
    fun setUserName(username: String) {
        sharedPreferences!!.edit().putString("userName",username).apply()
    }
    fun getLocalPassword():String{
            return sharedPreferences!!.getString("localPassword","").toString()
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

    fun setUserFIO(fio: String) {
        sharedPreferences!!.edit().putString("UserFio",fio).apply()
    }
    fun getUserFIO():String{
        return sharedPreferences!!.getString("UserFio","").toString()
    }

    fun setUserPasswordHash(stringDigest: String?) {
        sharedPreferences!!.edit().putString("UserPasswordHash",stringDigest).apply()
    }
    fun getUserPasswordHash():String{
        return sharedPreferences!!.getString("UserPasswordHash","").toString()
    }
    fun setUserRolesName(rolesname : String){
        sharedPreferences!!.edit().putString("user_roles",rolesname).apply()
    }
    fun getUserRolesName() : String{
        return sharedPreferences?.getString("user_roles","").toString()
    }
    fun setIsManager(isManager : Int){
        sharedPreferences!!.edit().putInt("is_manager",isManager).apply()
    }
    fun getIsManager() : Int{
        return sharedPreferences?.getInt("is_manager",-1)!!
    }
    fun setImageResource(imageRes : Int){
        sharedPreferences!!.edit().putInt("imageRes",imageRes).apply()
    }
    fun getImageResource() : Int{
        return sharedPreferences?.getInt("imageRes",-1)!!
    }
    fun getIsFirstTime() :Boolean {
        return sharedPreferences!!.getBoolean("is_first_time", true)
    }
    fun setIsFirstTime(isFirst: Boolean) {
        sharedPreferences!!.edit().putBoolean("is_first_time",isFirst).apply()
    }
    fun getUserTypesId() : Int {
        return sharedPreferences!!.getInt("user_types_id", -1)
    }
    fun setUserTypesId(userTypesId: Int) {
        sharedPreferences!!.edit().putInt("user_types_id",userTypesId).apply()
    }
    fun getBranchesId() : Int {
        return sharedPreferences!!.getInt("branches_id", -1)
    }
    fun setBranchesId(userTypesId: Int) {
        sharedPreferences!!.edit().putInt("branches_id",userTypesId).apply()
    }

    fun clearPreferences()
    {
        sharedPreferences!!.edit().putBoolean("is_first",true).apply()
        sharedPreferences!!.edit().putBoolean("is_first_time",true).apply()
        sharedPreferences!!.edit().putString("app_language","uz").apply()
        sharedPreferences!!.edit().putInt("user_id",0).apply()
        sharedPreferences!!.edit().putString("userName","").apply()
        sharedPreferences!!.edit().putString("localPassword","").apply()
        sharedPreferences!!.edit().putString("UserPassword","").apply()
        sharedPreferences!!.edit().putString("UserFio","").apply()
        sharedPreferences!!.edit().putString("UserPasswordHash","").apply()
        sharedPreferences!!.edit().putString("user_roles","").apply()
        sharedPreferences!!.edit().putInt("is_manager",-1).apply()
        sharedPreferences!!.edit().putInt("imageRes",-1).apply()
        sharedPreferences!!.edit().putInt("user_types_id",-1).apply()
        sharedPreferences!!.edit().putInt("branches_id",-1).apply()

    }
}