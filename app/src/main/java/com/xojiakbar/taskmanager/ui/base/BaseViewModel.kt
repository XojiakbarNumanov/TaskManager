package com.xojiakbar.taskmanager.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import java.lang.ref.WeakReference

abstract class BaseViewModel<R : BaseEmptyRouter?>(var application: Application) :
    AndroidViewModel(application) {
    private var router: WeakReference<R>? = null
//    var serialExecuter: SerialExecuter
//
//    init {
//        serialExecuter = SerialExecuter()
//        init()
//    }

    protected fun init() {}
    fun getRouter(): R? {
        return if (router != null) router!!.get() else null
    }

    fun setRouter(r: Nothing?) {
        router = WeakReference(r)
    }
}