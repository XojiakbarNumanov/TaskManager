package com.xojiakbar.taskmanager.Utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public abstract class BaseViewModel extends AndroidViewModel {


    Application application;



    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.application = application;

        init();
    }

    protected void init() {

    }

}
