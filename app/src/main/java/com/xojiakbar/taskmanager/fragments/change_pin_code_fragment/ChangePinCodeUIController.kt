package com.xojiakbar.taskmanager.fragments.change_pin_code_fragment

import android.content.Context
import android.content.res.ColorStateList
import android.os.Vibrator
import android.view.View
import android.view.animation.AnimationUtils
import androidx.databinding.BaseObservable
import com.google.android.material.textfield.TextInputLayout
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Preferences
import java.util.Objects

class ChangePinCodeUIController(context: Context) : BaseObservable() {
    var router : ChangePinCodeRouter? = null
    private var context : Context? =null
    init {
        this.context = context
    }

    fun onClickOk(currentView: TextInputLayout, view: TextInputLayout, repeatView: TextInputLayout?) {
        if (Preferences.getLocalPassword()
                .equals(currentView.editText!!.text.toString())
        ) {
            if (validator(view, repeatView!!)!!) {
                router?.onChange(view.editText?.text.toString())
            }
        } else {
            currentView.error = "Password - Error"
            currentView.setErrorTextColor(
                ColorStateList.valueOf(
                    context?.getResources()!!.getColor(R.color.red)
                )
            )
            currentView.defaultHintTextColor =
                ColorStateList.valueOf(context!!.getResources().getColor(R.color.red))
            shakeWithVibration(currentView)
        }
    }
    private fun validator(newView: TextInputLayout, confirmView: TextInputLayout): Boolean? {
        val newPas = newView.editText!!.text.toString()
        val confirmPas = confirmView.editText!!.text.toString()
        if (newPas.isEmpty()) {
            newView.error = context!!.getString(R.string.new_pin_code_cannot_be_empty)
            shakeWithVibration(newView)
            return false
        }
        if (confirmPas.isEmpty()) {
            confirmView.error = context!!.getString(R.string.enter_confirm_pin_code)
            shakeWithVibration(confirmView)
            return false
        }
        if (confirmPas != newPas) {
            confirmView.error = context!!.getString(R.string.pin_code_do_not_match)
            shakeWithVibration(confirmView)
            return false
        }
        return true
    }
    fun shakeWithVibration(view: View) {
        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
        view.startAnimation(shake)
        (Objects.requireNonNull<Any>(context!!.getSystemService(Context.VIBRATOR_SERVICE)) as Vibrator).vibrate(
            300
        )
    }
    fun onBack(){
        router?.onBack()
    }

}