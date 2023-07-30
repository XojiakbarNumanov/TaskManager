package com.xojiakbar.taskmanager.Utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

import java.lang.ref.WeakReference;

public abstract class BaseViewModel<R extends BaseEmptyRouter> extends AndroidViewModel {


    private WeakReference<R> router;
    Application application;



    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.application = application;

        init();
    }

    protected void init() {

    }
    @Nullable
    public R getRouter() {
        if (router != null)
            return router.get();
        else
            return null;
    }

    public void setRouter(R r) {
        router = new WeakReference<>(r);
    }


}
