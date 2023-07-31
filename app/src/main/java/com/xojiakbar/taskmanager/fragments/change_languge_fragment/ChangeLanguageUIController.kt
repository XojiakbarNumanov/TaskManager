package com.xojiakbar.taskmanager.fragments.change_languge_fragment

import android.app.Activity
import android.content.Context
import androidx.databinding.BaseObservable
import com.xojiakbar.taskmanager.Utils.LanguageHalper
import com.xojiakbar.taskmanager.Utils.Preferences
import java.util.Locale

class ChangeLanguageUIController(activity: Activity) : BaseObservable() {
    private var activity : Activity? = null
    var router : ChangeLangugeRouter? =null

    init {
        this.activity = activity
    }

    fun onUzLotin() {
        setLanguage("uz")
    }

    fun onRu() {
        setLanguage("ru")
    }

    fun onUzKiril() {
        setLanguage("uk")
    }

    private fun setLanguage(lang: String) {
        Preferences.setAppLanguage(lang)
        LanguageHalper.setLanguageResourse(activity!!)
        router?.back()
    }

}