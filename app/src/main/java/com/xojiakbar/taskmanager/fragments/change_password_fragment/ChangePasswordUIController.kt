package com.xojiakbar.taskmanager.fragments.change_password_fragment

import android.content.Context
import android.content.res.ColorStateList
import android.os.Vibrator
import android.view.View
import android.view.animation.AnimationUtils
import androidx.databinding.BaseObservable
import com.google.android.material.textfield.TextInputLayout
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.fragments.create_pin_code.CreatePinCodeRouter
import java.util.Objects

class ChangePasswordUIController(context: Context) : BaseObservable(){
    var router: ChangePasswordRouter? = null
    private var context: Context

    init {
        this.context = context
    }
    fun onClickOk(currentView: TextInputLayout, view: TextInputLayout, repeatView: TextInputLayout?) {
        if (Preferences.getUserPassword()
                .equals(currentView.editText!!.text.toString())
        ) {
            if (validator(view, repeatView!!)!!) {
                router?.changePassword(view.editText!!.text.toString())
            }
        } else {
            currentView.error = "Password - Error"
            currentView.setErrorTextColor(
                ColorStateList.valueOf(
                    context.getResources().getColor(R.color.red)
                )
            )
            currentView.defaultHintTextColor =
                ColorStateList.valueOf(context.getResources().getColor(R.color.red))
            shakeWithVibration(currentView)
        }
    }
    private fun validator(newView: TextInputLayout, confirmView: TextInputLayout): Boolean? {
        val newPas = newView.editText!!.text.toString()
        val confirmPas = confirmView.editText!!.text.toString()
        if (newPas.isEmpty()) {
            newView.error = context.getString(R.string.new_password_cannot_be_empty)
            shakeWithVibration(newView)
            return false
        }
        if (checkCycrilic(newPas)) {
            newView.editText!!.error = context.getString(R.string.cycrilic_error)
            return false
        }
        if (confirmPas.isEmpty()) {
            confirmView.error = context.getString(R.string.enter_confirm_password)
            shakeWithVibration(confirmView)
            return false
        }
        if (confirmPas != newPas) {
            confirmView.error = context.getString(R.string.passwords_do_not_match)
            shakeWithVibration(confirmView)
            return false
        }
        return true
    }
    fun shakeWithVibration(view: View) {
        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
        view.startAnimation(shake)
        (Objects.requireNonNull<Any>(context.getSystemService(Context.VIBRATOR_SERVICE)) as Vibrator).vibrate(
            300
        )
    }
    private fun checkCycrilic(str: String): Boolean {
        for (c in str.toCharArray()) {
            if ((c < '0' || c > '9') && (c < 'A' || c > 'Z') && (c < 'a' || c > 'z')) return true
        }
        return false
    }

}

