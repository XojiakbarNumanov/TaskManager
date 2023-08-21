package com.xojiakbar.taskmanager.fragments.dashboard_fragment

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.xojiakbar.taskmanager.Utils.Preferences

class DashboardUIController(context: Context) : BaseObservable() {
    var router : DashboardRouter?= null

    @Bindable
    fun getUserName(): String? {
        return Preferences.getUserFIO()
    }
    @Bindable
    fun getJobs(): String? {
        return Preferences.getUserRolesName()
    }

    fun refresh(){
        router?.refresh()
    }
    @Bindable
    fun getIsManager(): Boolean{
        return Preferences.getUserTypesId() == 4
    }


}