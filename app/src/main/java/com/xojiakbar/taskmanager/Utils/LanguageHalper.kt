package com.xojiakbar.taskmanager.Utils

import android.app.Activity
import java.util.Locale

object LanguageHalper {
    fun setLanguageResourse(activity: Activity) {
        val res = activity.resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(Locale(Preferences.getAppLanguage()))
        res.updateConfiguration(conf, dm)
    }
}