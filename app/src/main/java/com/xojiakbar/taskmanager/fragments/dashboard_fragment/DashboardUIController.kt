package com.xojiakbar.taskmanager.fragments.dashboard_fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Looper
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.squareup.picasso.Picasso
import com.xojiakbar.taskmanager.BuildConfig
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Preferences
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import com.xojiakbar.taskmanager.BR


class DashboardUIController(val context: Context) : BaseObservable() {
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