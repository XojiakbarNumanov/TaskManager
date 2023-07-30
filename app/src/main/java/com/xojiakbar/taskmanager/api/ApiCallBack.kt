package com.xojiakbar.taskmanager.api

import com.xojiakbar.taskmanager.api.result.ErrorResult

interface ApiCallback<T> {
    fun onSuccess(response: T)
    fun onError(throwable: Throwable)
    fun onErrorMsg(errorMsg: ErrorResult)
}
