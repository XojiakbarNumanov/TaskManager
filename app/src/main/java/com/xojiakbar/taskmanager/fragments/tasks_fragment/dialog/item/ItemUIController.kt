package com.xojiakbar.taskmanager.fragments.home_fragment.dialog.item

import android.content.Context
import androidx.databinding.BaseObservable
import com.xojiakbar.taskmanager.BR
import com.xojiakbar.taskmanager.fragments.home_fragment.dialog.SendInspectionDialogRouter
import java.io.File

class ItemUIController (val context: Context) : BaseObservable(){
    var router : SendInspectionDialogRouter? =null
    var images: File? = null
    fun setimage(image : File){
        this.images = image
        notifyPropertyChanged(BR._all)
    }
}