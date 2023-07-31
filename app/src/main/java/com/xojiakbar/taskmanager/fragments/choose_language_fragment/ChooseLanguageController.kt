package com.xojiakbar.taskmanager.fragments.choose_language_fragment

import android.app.Activity
import android.util.Log
import androidx.databinding.BaseObservable
import com.xojiakbar.taskmanager.Utils.LanguageHalper
import com.xojiakbar.taskmanager.Utils.Preferences
import java.util.Locale

class ChooseLanguageController(activity: Activity) : BaseObservable(){

    private var activity : Activity? = null
    var router : ChooseLanguageRouter? =null

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
            if (Preferences.getIsFirst())  Preferences.setIsFirst(false)
            LanguageHalper.setLanguageResourse(activity!!)
            router?.showLoginFragment()
        }

}