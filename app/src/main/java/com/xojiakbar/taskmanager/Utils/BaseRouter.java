package com.xojiakbar.taskmanager.Utils;


import com.xojiakbar.taskmanager.api.result.ErrorResult;


public interface BaseRouter<T> extends BaseEmptyRouter{

    void setLoading(String title);

    void onSuccess(T response);

    void onError(ErrorResult errorMsg);

}
