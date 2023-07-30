package com.xojiakbar.taskmanager.fragments.password_fragment

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.view.View
import android.view.animation.AnimationUtils
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Preferences
import java.util.Objects

class PasswordUIController(context: Context) : BaseObservable() {
    var router: PasswordRouter? = null
    private var context: Context? = null

    lateinit var pinCode: String
    init {
        pinCode = ""
        this.context = context
    }

    fun onBackspaceClick() {
        if (pinCode.isEmpty()) return
        (Objects.requireNonNull(context!!.getSystemService(Context.VIBRATOR_SERVICE)) as Vibrator).vibrate(
            20
        )
        pinCode = pinCode.substring(0, pinCode.length - 1)
        notifyPropertyChanged(BR._all)
    }
    fun onNumberClick(v: View, layout: View) {
        if (pinCode.length < 4) {
            pinCode += v.tag.toString()
            notifyPropertyChanged(BR._all)
            (Objects.requireNonNull<Any>(context?.getSystemService(Context.VIBRATOR_SERVICE)) as Vibrator).vibrate(
                50
            )
        }
        if (pinCode.length == 4) {
            Handler(Looper.getMainLooper()).postDelayed({
                if (checkPIN()) {
                    val anim = AnimationUtils.loadAnimation(
                        context,
                        R.anim.alpha_animate
                    )
                    layout.startAnimation(anim)
                    router?.showDashboard()
                } else {
                    pinCode = ""
                    notifyPropertyChanged(BR._all)
                    val shake =
                        AnimationUtils.loadAnimation(context, R.anim.shake)
                    layout.startAnimation(shake)
                }
            }, 20)
        }
    }
    private fun checkPIN(): Boolean {
        return Preferences.getLocalPassword() == pinCode
    }
    @Bindable
    fun getEnableNumber(): Boolean {
        return pinCode.length < 4
    }
    @Bindable
    fun getImage1(): Boolean {
        return pinCode.length > 0
    }

    @Bindable
    fun getImage2(): Boolean {
        return pinCode.length > 1
    }

    @Bindable
    fun getImage3(): Boolean {
        return pinCode.length > 2
    }

    @Bindable
    fun getImage4(): Boolean {
        return pinCode.length > 3
    }
}